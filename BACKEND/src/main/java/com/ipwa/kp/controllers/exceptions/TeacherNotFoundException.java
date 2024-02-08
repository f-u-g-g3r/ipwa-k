package com.ipwa.kp.controllers.exceptions;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(Long id) {
        super("Teacher with id '" + id + "' not found");
    }
}
