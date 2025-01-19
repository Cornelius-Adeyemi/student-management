package com.adebisi.student_management.controller;


import com.adebisi.student_management.dto.request.LoginDTO;
import com.adebisi.student_management.dto.response.GeneralResponseDTO;
import com.adebisi.student_management.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/login")
    private ResponseEntity<  GeneralResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO){

        return ResponseEntity.ok().body(authenticationService.login(loginDTO));

    }
}
