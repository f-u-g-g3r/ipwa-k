package com.ipwa.kp.controllers.requests;

public class TeacherPatchRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String username;

    public TeacherPatchRequest() {
    }

    public TeacherPatchRequest(String firstName, String lastName, String email, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
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

    public String getUsername() {
        return username;
    }
}
