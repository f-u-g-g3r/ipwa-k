package com.ipwa.kp.controllers.exceptions;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(Long id) {
        super("Company with id '" + id + "' not found");
    }
}
