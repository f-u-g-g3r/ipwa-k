package com.ipwa.kp.controllers.exceptions;

public class CoordinatorNotFoundException extends RuntimeException {
    public CoordinatorNotFoundException(Long id) {
        super("Coordinator with id '" + id + "' not found");
    }
}
