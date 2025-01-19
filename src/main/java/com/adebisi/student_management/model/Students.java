package com.adebisi.student_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Students extends AuditEntity {

    @Column(unique = true)
    private String studentId;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn( name = "User_id", referencedColumnName = "id")
    private Users users;

    @Column(nullable = false)
    private Boolean profileDeleted = false;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "student")
    private List<Grade> subjects = new ArrayList<>();



}
