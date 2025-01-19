package com.adebisi.student_management.constant;


import lombok.Getter;

@Getter
public enum Subject {

    MATH("math"), ENGLISH("english"), PHYSICS("physics"), CHEMISTRY("chemistry"), BIOLOGY("biology");

    private final String subject;


    Subject(String subject){
        this.subject = subject;

    }
}
