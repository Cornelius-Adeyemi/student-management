package com.adebisi.student_management.repository;


import com.adebisi.student_management.model.Students;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Students, Long> {

    Optional<Students> findFirstByStudentId(String studentId);

    List<Students> findAllByProfileDeletedFalse();

    List<Students> findAllByProfileDeletedFalse(Pageable pageable);

    List<Students> findAllByProfileDeletedFalseAndSubjectsIsNotEmpty();

}
