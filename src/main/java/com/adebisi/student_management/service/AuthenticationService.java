package com.adebisi.student_management.service;

import com.adebisi.student_management.dto.request.LoginDTO;
import com.adebisi.student_management.dto.response.GeneralResponseDTO;

public interface AuthenticationService {


    GeneralResponseDTO login(LoginDTO loginDTO);
}
