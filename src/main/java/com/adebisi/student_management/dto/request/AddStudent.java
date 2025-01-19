package com.adebisi.student_management.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class AddStudent {

    @NotEmpty(message = "Name must not be null or an empty String")
    @NotNull(message = "firstname must not be null or an empty String")
    private String firstName;

    @NotEmpty(message = "lastname must not be null or an empty String")
    @NotNull(message = "last must not be null or an empty String")
    private String lastName;

    @NotEmpty(message = "email must not be null or an empty String")
    @NotNull(message = "email must not be null or an empty String")
    private String email;

    @NotEmpty(message = "password must not be null or an empty String")
    @NotNull(message = "password must not be null or an empty String")
    private String password;
}
