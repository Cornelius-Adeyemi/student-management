package com.adebisi.student_management.repository;

import com.adebisi.student_management.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long > {
}
