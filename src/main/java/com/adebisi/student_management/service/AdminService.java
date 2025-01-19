package com.adebisi.student_management.service;

import com.adebisi.student_management.dto.request.AddStudent;
import com.adebisi.student_management.dto.request.GradeStudent;
import com.adebisi.student_management.dto.response.GeneralResponseDTO;
import com.adebisi.student_management.service.serviceImpl.AdminServiceImpl;

import java.util.Map;

public interface AdminService {


     GeneralResponseDTO addAStudent(AddStudent addStudent);


     GeneralResponseDTO addStudentGrade(GradeStudent gradeStudent);

     GeneralResponseDTO generateReportForStudents();

     GeneralResponseDTO getAllStudent(int pageNum, int pageSize);
}
