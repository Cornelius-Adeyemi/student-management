package com.adebisi.student_management.service.serviceImpl;

import com.adebisi.student_management.constant.Role;
import com.adebisi.student_management.dto.request.AddStudent;
import com.adebisi.student_management.dto.request.GradeStudent;
import com.adebisi.student_management.dto.request.SubjectGrade;
import com.adebisi.student_management.dto.response.GeneralResponseDTO;
import com.adebisi.student_management.exception_handler.Errors;
import com.adebisi.student_management.exception_handler.GeneralException;
import com.adebisi.student_management.model.Grade;
import com.adebisi.student_management.model.Students;
import com.adebisi.student_management.model.Users;
import com.adebisi.student_management.repository.GradeRepository;
import com.adebisi.student_management.repository.StudentRepository;
import com.adebisi.student_management.repository.UserRepository;
import com.adebisi.student_management.service.AdminService;
import com.adebisi.student_management.util.StudentDTOMapper;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;


@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final StudentRepository studentRepository;
    private final EntityManager entityManager;

    private final GradeRepository gradeRepository;


    @Override
    @Transactional
    public GeneralResponseDTO addAStudent(AddStudent addStudent) {
        log.info("adding student");
        try {
            Optional<Users> usersChecker = userRepository.findByEmail(addStudent.getEmail());

            if (usersChecker.isPresent()) {
                throw new GeneralException(Errors.USER_EMAIL_ALREADY_EXIST, addStudent.getEmail());
            }

            Users users = new Users();

            users.setEmail(addStudent.getEmail());

            users.setPassword(passwordEncoder.encode(addStudent.getPassword()));

            users.setFirstName(addStudent.getFirstName());

            users.setLastName(addStudent.getLastName());

            users.setIsDisable(false);

            users.setRole(Role.STUDENT);


            Users savedUser = userRepository.save(users);

            Students students = new Students();

            students.setStudentId(getStudentId());

            students.setUsers(savedUser);
            studentRepository.save(students);


            return GeneralResponseDTO.builder()
                    .message("Student successfully created, id : "+ students.getStudentId())
                    .build();

        }catch (Exception e){

           log.info("error creating student- error message: {}", e.getMessage());
            throw new GeneralException("Error creating a student user", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }



    }


    public GeneralResponseDTO addStudentGrade(GradeStudent gradeStudent){
        log.info("adding student grade");
        Map<String, Object> res =   validateStudentGrade(gradeStudent);

        Students students = (Students) res.get("student");
       if( students.getSubjects().size() == 5 ){
           throw new GeneralException("Student has already been graded", HttpStatus.BAD_REQUEST, null);
       }

        List<Grade> grades = (List<Grade>) res.get("grade");

        students.getSubjects().addAll(grades);

        studentRepository.save(students);

       // gradeRepository.saveAll(grades);

         return GeneralResponseDTO.builder()
                 .message(students.getStudentId() + " has been successfully graded")
                 .success(true)
                 .build();



    }



    public GeneralResponseDTO getAllStudent(int pageNum, int pageSize){
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdTime").descending());

        List<Students> students = studentRepository.findAllByProfileDeletedFalse(pageable);

        List<Map<String, String>>  studentsList =  students.stream().map(e->{
           Map<String, String> response = new HashMap<>();
           response.put("firstname", e.getUsers().getFirstName());
            response.put("lastname", e.getUsers().getLastName());
            response.put("Student-id", e.getStudentId());
            response.put("email", e.getUsers().getEmail());

           return response;

        }).toList();


        return GeneralResponseDTO.builder()
                .success(true)
                .message("processed successfully")
                .data(studentsList)
                .build();


    }

    public GeneralResponseDTO generateReportForStudents(){

        log.info("generating student report");
      try {
          Map<String, Report> response = new HashMap<>();


          List<Students> studentsList = studentRepository.findAllByProfileDeletedFalse();

         List<StudentDTOMapper> studentsDTO = studentsList.stream().map(StudentDTOMapper::map).toList();

          CompletableFuture<HashMap<String, List<Double>>> modeFuture = CompletableFuture.supplyAsync(() -> {

              return calculateMode(studentsDTO);
          });

          CompletableFuture<HashMap<String, Double>> meanFuture = CompletableFuture.supplyAsync(() -> {

              return calculateMean(studentsDTO);
          });

          CompletableFuture<HashMap<String, Double>> medianFuture = CompletableFuture.supplyAsync(() -> {

              return calculateMedian(studentsDTO);
          });

          HashMap<String, List<Double>> mode = modeFuture.get();

          HashMap<String, Double> mean = meanFuture.get();

          HashMap<String, Double> median =medianFuture.get();

//          HashMap<String, List<Double>> mode = calculateMode(studentsList);
//
//          HashMap<String, Double> mean = calculateMean(studentsList);
//
//          HashMap<String, Double> median = calculateMedian(studentsList);




          for (Students students : studentsList) {

              Report report = new Report();

              report.setFirstname(students.getUsers().getFirstName());
              report.setLastname(students.getUsers().getLastName());
              report.setMean(mean.get(students.getStudentId()));
              report.setMode(mode.get(students.getStudentId()));
              report.setMedian(median.get(students.getStudentId()));

              report.setSubjectGrades(students.getSubjects().stream().map(e -> {
                  SubjectGrade subjectGrade = new SubjectGrade();
                  subjectGrade.setScore(e.getScores());
                  subjectGrade.setSubject(e.getSubjectName());

                  return subjectGrade;
              }).toList());

              response.put(students.getStudentId(), report);
          }


          return GeneralResponseDTO.builder()
                  .message("Report generated")
                  .success(true)
                  .data(response)
                  .build();



      }catch (Exception e){

          log.info("{}",e );

          throw new GeneralException("Error generating report", HttpStatus.INTERNAL_SERVER_ERROR, null);
      }
    }


    private HashMap<String, List<Double>> calculateMode(List<StudentDTOMapper> students){


        HashMap<String, List<Double>> response = new HashMap<>();

        students.forEach(e ->{
            ArrayList<Double> scores = new ArrayList<>();
            double median = 0;
            e.getSubjects().forEach( y ->{
                scores.add(y.getScore());
            });

            Map<Double, Integer> frequencyMap = new HashMap<>();

            for (double number : scores) {
                frequencyMap.put(number, frequencyMap.getOrDefault(number, 0) + 1);
            }


            int maxFrequency = Collections.max(frequencyMap.values());


            List<Double> modes = new ArrayList<>();
            for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
                if (entry.getValue() == maxFrequency) {
                    modes.add(entry.getKey());
                }
            }


            response.put(e.getStudentId(), modes);

        });

        return response;

    }


   private HashMap<String, Double> calculateMean(List<StudentDTOMapper> students){

        HashMap<String, Double> response = new HashMap<>();

        students.forEach(e ->{
            double score = 0.0;
            int  count = 0;
            for(SubjectGrade grade : e.getSubjects()){
                score += grade.getScore();
                count++;
            }
             double mean = score/count;
           response.put(e.getStudentId(), mean);
        });


        return response;
   }





    private HashMap<String, Double> calculateMedian(List<StudentDTOMapper> students){

        HashMap<String, Double> response = new HashMap<>();

        students.forEach(e ->{
             ArrayList<Double> scores = new ArrayList<>();
             double median = 0;
           e.getSubjects().forEach( y ->{
               scores.add(y.getScore());
           });

           Collections.sort(scores);

            int size = scores.size();
            if (size % 2 == 1) {

             median  =   scores.get(size / 2);
            } else {

                double middle1 = scores.get(size / 2 - 1);
                double middle2 = scores.get(size / 2);
                median =  (middle1 + middle2) / 2.0;
            }

            response.put(e.getStudentId(), median);
        });

        return response;
    }


    private  Map<String, Object> validateStudentGrade(GradeStudent gradeStudent){

        Map<String, Boolean> subject = new HashMap<>();

    Students students =  studentRepository.findFirstByStudentId(gradeStudent.getStudentId()).orElseThrow(
                ()-> new GeneralException("Student Id doesn't exist", HttpStatus.BAD_REQUEST, null)
        );

    List<Grade> grades =  new ArrayList<>();

        List< SubjectGrade > studentGrade = gradeStudent.getSubjectGrades();

        for(SubjectGrade grade : studentGrade){

            if(subject.containsKey(grade.getSubject().getSubject())){

                throw new GeneralException("You can pass a subject twice", HttpStatus.BAD_REQUEST, null);
            }else {

                subject.put(grade.getSubject().getSubject(), true);
            }
           Grade grade1 = new Grade();

            grade1.setSubjectName(grade.getSubject());
            grade1.setScores(grade.getScore());
            grade1.setStudent(students);
            grades.add(grade1);
        }


        Map<String, Object>  response  = new HashMap<>();

        response.put("student",students );

        response.put("grade", grades);

        return response;
    }


    private String getStudentId(){

     String id =    "S-".concat(RandomStringUtils.randomNumeric(4));

        Optional<Students> students = studentRepository.findFirstByStudentId(id);

        if(students.isPresent()){
            getStudentId();
        }


        return id;
    }


    @Getter
    @Setter
   public class Report{

        private String firstname;

        private String lastname;

        private  Double mean;

        private  Double median;

        private List<SubjectGrade> subjectGrades;

        private List<Double> mode;




    }
}
