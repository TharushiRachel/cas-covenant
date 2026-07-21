package com.itechro.cas.model.security;

import com.itechro.cas.model.dto.master.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CasUserDetails implements UserDetails {
    private UserDTO user;
    private List<GrantedAuthority> authorities;

    public CasUserDetails(UserDTO user) {
        this.user = user;
        this.authorities = generateAuthorities(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    public String getUpmGroupCode() {
        return user.getUpmGroupCode();
    }

    public String getDivCode() {
        return user.getDivCode();
    }

    public String getDisplayName() {
        return user.getFirstName() + " " + user.getLastName();
    }

    public boolean getIsAssistant() {
        return user.getIsAssistantUser();
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
        return true;
    }

    private List<GrantedAuthority> generateAuthorities(UserDTO user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getPrivileges().forEach((privilege) -> authorities.add(new SimpleGrantedAuthority(privilege)));
        return authorities;
    }


    public Set<String> getPrivileges() {
        return this.user.getPrivileges();
    }

    public Integer getUserId() {
        return user.getUserID();
    }

    public boolean isAgent() {
        return user.isAgent();
    }

    @Override
    public String toString() {
        return "CasUserDetails{" +
                "user=" + user +
                ", authorities=" + authorities +
                '}';
    }
}
