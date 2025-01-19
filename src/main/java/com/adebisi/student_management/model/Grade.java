package com.adebisi.student_management.model;

import com.adebisi.student_management.constant.Subject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Grade extends  AuditEntity{

    @Enumerated(EnumType.STRING)
    private Subject subjectName;

    private Double scores;


    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Students student;

}
