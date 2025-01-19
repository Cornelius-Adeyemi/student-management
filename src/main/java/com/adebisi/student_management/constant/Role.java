package com.adebisi.student_management.constant;


import lombok.Getter;

@Getter
public enum Role {

    ADMIN("admin"), STUDENT("student");


    private final String role;


    Role(String role){

       this. role = role;
    }
}
