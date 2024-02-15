package com.ipwa.kp.controllers.exceptions;

public class ClassGroupNotFoundException extends RuntimeException {
    public ClassGroupNotFoundException(Long id) {
        super("Class group with id '" + id + "' not found");
    }
    public ClassGroupNotFoundException(String name) {
        super("Class group with name '" + name + "' not found");
    }
}
