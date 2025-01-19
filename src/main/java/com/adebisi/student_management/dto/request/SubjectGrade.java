package com.adebisi.student_management.dto.request;


import com.adebisi.student_management.constant.Subject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubjectGrade{


    private Subject subject;

    @Max(value = 100L, message = "Message should not be more than 100")
    private Double score;



}

