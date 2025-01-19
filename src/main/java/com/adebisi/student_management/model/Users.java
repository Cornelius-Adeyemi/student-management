package com.adebisi.student_management.model;


import com.adebisi.student_management.config.security.CustomerUserDetails;
import com.adebisi.student_management.constant.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "root_users")
@Getter
@Setter
public class Users extends  AuditEntity implements CustomerUserDetails {


    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;


   @Column(unique = true)
    private String email;

    private String password;


    @Column(nullable = false)
    private Boolean isDisable = false;


    public Role getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.toString()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isDisable;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isDisable;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isDisable;
    }

    @Override
    public boolean isEnabled() {
        return !isDisable;
    }
}
