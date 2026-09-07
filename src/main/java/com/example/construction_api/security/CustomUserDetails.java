package com.example.construction_api.security;

import com.example.construction_api.model.persisitece.Employer;
import com.example.construction_api.model.persisitece.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    final Employer employer;

    public CustomUserDetails(Employer employer) {
        this.employer = employer;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        employer.getRoles().forEach(
                role -> authorities.add(new SimpleGrantedAuthority(role.getAppUserRole()))

        );
        return authorities;
    }

    public Long getEmployerId() {
        return employer.getEmployerId();
    }

    public String getFullName() {
        return employer.getFullName();
    }


    public Long getCreateDateTime() {
        return employer.getCreateDateTime();
    }

    public String getRole() {

        Role role = employer.getRoles().iterator().next();
        return role.getAppUserRole();
    }



    @Override
    public String getPassword() {
        return employer.getPassword();
    }

    @Override
    public String getUsername() {
        return employer.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return employer.getEnabled();
    }
}
