package com.adebisi.student_management.controller;


import com.adebisi.student_management.dto.request.AddStudent;
import com.adebisi.student_management.dto.request.GradeStudent;
import com.adebisi.student_management.dto.response.GeneralResponseDTO;
import com.adebisi.student_management.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/")
@RequiredArgsConstructor
public class AdminController {


    private final AdminService adminService;

    @PostMapping("add-student")
    public GeneralResponseDTO addStudent(@Valid @RequestBody AddStudent addStudent){


        return adminService.addAStudent(addStudent);

    }

    @PostMapping("grade-student")
    public GeneralResponseDTO addStudentGrade(@Valid @RequestBody GradeStudent gradeStudent){


        return adminService.addStudentGrade(gradeStudent);

    }

   @GetMapping("generate-report")
    public GeneralResponseDTO generateReport(){


        return adminService.generateReportForStudents();

    }

    @GetMapping("get-all-student")
    public GeneralResponseDTO addStudentGrade(@RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
                                              @RequestParam(name= "pageSize", defaultValue = "20", required = false) int pageSize){


        return adminService.getAllStudent(pageNo,pageSize);

    }
}
