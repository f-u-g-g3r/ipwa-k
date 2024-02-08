package com.ipwa.kp.controllers.exceptions;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(Long id) {
        super("Student with id '" + id + "' not found");
    }
}
