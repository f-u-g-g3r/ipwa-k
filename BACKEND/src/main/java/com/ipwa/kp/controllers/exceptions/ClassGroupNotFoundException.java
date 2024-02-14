package com.ipwa.kp.controllers.exceptions;

public class ClassGroupNotFoundException extends RuntimeException {
    public ClassGroupNotFoundException(Long id) {
        super("Class group with id '" + id + "' not found");
    }
}
