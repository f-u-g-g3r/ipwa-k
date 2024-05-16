package com.ipwa.kp.advices;

import com.ipwa.kp.controllers.exceptions.CompanyNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class CompanyNotFoundAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CompanyNotFoundException.class)
    protected ResponseEntity<Object> companyNotFoundHandler(CompanyNotFoundException e, WebRequest request) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return handleExceptionInternal(e, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
