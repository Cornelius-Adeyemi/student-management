package com.adebisi.student_management.repository;

import com.adebisi.student_management.constant.Role;
import com.adebisi.student_management.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users>  findByEmail(String email);

    Optional<Users> findFirstByRole(Role role);
}
