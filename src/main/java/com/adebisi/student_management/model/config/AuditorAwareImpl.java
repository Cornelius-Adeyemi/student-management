package com.adebisi.student_management.model.config;


import com.adebisi.student_management.config.security.SecurityDetailsHolder;
import com.adebisi.student_management.util.GetLoginUser;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        SecurityDetailsHolder loginUser = GetLoginUser.getLoginUser();

        if( loginUser != null &&  loginUser.isAuthenticated() ){

            return Optional.of(loginUser.getEmail());
        }

        return Optional.empty();
    }
}
