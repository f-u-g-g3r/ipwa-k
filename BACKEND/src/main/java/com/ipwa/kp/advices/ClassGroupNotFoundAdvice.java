package com.ipwa.kp.advices;

import com.ipwa.kp.controllers.exceptions.ClassGroupNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ClassGroupNotFoundAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ClassGroupNotFoundException.class)
    protected ResponseEntity<Object> classGroupNotFoundHandler(ClassGroupNotFoundException e, WebRequest request) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return handleExceptionInternal(e, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
