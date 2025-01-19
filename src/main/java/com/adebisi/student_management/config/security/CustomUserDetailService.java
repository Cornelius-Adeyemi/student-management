package com.adebisi.student_management.config.security;

import com.adebisi.student_management.exception_handler.GeneralException;
import com.adebisi.student_management.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Primary
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomerUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        return userRepository.findByEmail(username).orElseThrow(
                  ()->
                           new GeneralException(username + " not found with username or email", HttpStatus.NOT_FOUND, null)

          );


    }
}
