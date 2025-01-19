package com.adebisi.student_management.config.security;


import com.adebisi.student_management.constant.Role;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomerUserDetails extends UserDetails {


    public String getEmail();

    public Role getRole();


}
