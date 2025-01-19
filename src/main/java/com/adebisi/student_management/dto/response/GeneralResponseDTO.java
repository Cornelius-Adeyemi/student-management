package com.adebisi.student_management.dto.response;


import lombok.*;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GeneralResponseDTO implements Serializable {

    private String message;

    private String status;

    private boolean success;

    private Object data;

    private Object errors;
}
