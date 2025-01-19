package com.adebisi.student_management.dto.request;


import com.adebisi.student_management.constant.Subject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GradeStudent {

    private String studentId;

    private List<SubjectGrade> subjectGrades;


}
