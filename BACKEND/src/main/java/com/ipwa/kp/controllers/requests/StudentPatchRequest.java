package com.ipwa.kp.controllers.requests;

import com.ipwa.kp.models.enums.Status;

public class StudentPatchRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Status accountStatus;

    public StudentPatchRequest() {
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Status getAccountStatus() {
        return accountStatus;
    }
}
