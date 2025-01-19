package com.adebisi.student_management.util;

import com.adebisi.student_management.dto.request.AddStudent;
import com.adebisi.student_management.dto.request.GradeStudent;
import com.adebisi.student_management.dto.request.SubjectGrade;
import com.adebisi.student_management.model.Students;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTOMapper {

     private AddStudent user;

    private List<SubjectGrade> subjects;

    private  String studentId;



    public static  StudentDTOMapper map (Students students){

        StudentDTOMapper studentDTOMapper = new StudentDTOMapper();


         AddStudent addStudent = new AddStudent();

         addStudent.setEmail(students.getUsers().getEmail());
         addStudent.setFirstName(students.getUsers().getFirstName());
         addStudent.setLastName(students.getUsers().getLastName());

         studentDTOMapper.setUser(addStudent);


         studentDTOMapper.setStudentId(students.getStudentId());

         List<SubjectGrade> grades = new ArrayList<>();



         students.getSubjects().forEach(e->{
             SubjectGrade subjectGrade = new SubjectGrade();

             subjectGrade.setSubject(e.getSubjectName());
             subjectGrade.setScore(e.getScores());

             grades.add(subjectGrade);

         });

         studentDTOMapper.setSubjects(grades);

         return studentDTOMapper;
    }

}
