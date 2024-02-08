package com.ipwa.kp.security.auth;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticationResponse {
    private String token;
    private Long id;
    private Collection<? extends GrantedAuthority> authority;

    private String errorMessage;

    public AuthenticationResponse() {}
    public AuthenticationResponse(String message) {
        errorMessage = message;
    }

    public AuthenticationResponse(String token, Long id, Collection<? extends GrantedAuthority> authority) {
        this.token = token;
        this.id = id;
        this.authority = authority;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Collection<? extends GrantedAuthority> getAuthority() {
        return authority;
    }

    public void setAuthority(Collection<? extends GrantedAuthority> authority) {
        this.authority = authority;
    }
}
