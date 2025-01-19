package com.adebisi.student_management.service.serviceImpl;

import com.adebisi.student_management.config.jwt.JwtService;
import com.adebisi.student_management.config.security.CustomUserDetailService;
import com.adebisi.student_management.config.security.CustomerUserDetails;
import com.adebisi.student_management.dto.request.LoginDTO;
import com.adebisi.student_management.dto.response.GeneralResponseDTO;
import com.adebisi.student_management.exception_handler.Errors;
import com.adebisi.student_management.exception_handler.GeneralException;
import com.adebisi.student_management.repository.UserRepository;
import com.adebisi.student_management.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {


    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailService userDetailsService;

    private final JwtService jwtService;





    @Override
    public GeneralResponseDTO login(LoginDTO loginDTO) {


        log.info("-----------------> {} Login ",loginDTO.getEmail());
        CustomerUserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());

        if(!passwordEncoder.matches(loginDTO.getPassword(), userDetails.getPassword())){// check if password is valid
            throw new GeneralException(Errors.INVALID_PASSWORD,null);
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                loginDTO.getPassword());

        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken); // // this checks all necessary field to see if the user is valid and not disable
        }catch (Exception e){
            throw new GeneralException(e.getMessage(), HttpStatus.UNAUTHORIZED,null );
        }



        return   generateOtpCode( userDetails);



    }

    private GeneralResponseDTO generateOtpCode(CustomerUserDetails userDetails){

        String  token = jwtService.generateToken(userDetails);
        HashMap<String, String> tokenObject = new HashMap<>();

        tokenObject.put("token", token);

        return GeneralResponseDTO.builder()
                .message("Login successful")
                .success(true)
                .data(tokenObject)
                .build();


    }
}
