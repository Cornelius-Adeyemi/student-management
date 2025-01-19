package com.adebisi.student_management.util;

import com.adebisi.student_management.constant.Role;
import com.adebisi.student_management.model.Users;
import com.adebisi.student_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class AdminInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.password:}")
    private String adminPassword;
    @Value("${admin.email:}")
    private String adminEmail;

    @Autowired
    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Optional<Users> user  = userRepository.findFirstByRole(Role.ADMIN);
        if(user.isEmpty()){
            Users admin = new Users();
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setEmail(adminEmail);
            admin.setIsDisable(false);
            admin.setRole(Role.ADMIN);
            admin.setPassword(passwordEncoder.encode(adminPassword.trim()));

              userRepository.save(admin);
        }


    }
}
