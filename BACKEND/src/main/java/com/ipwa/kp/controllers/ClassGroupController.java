package com.ipwa.kp.controllers;

import com.ipwa.kp.repositories.ClassGroupRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassGroupController {
    private final ClassGroupRepository repository;

    public ClassGroupController(ClassGroupRepository repository) {
        this.repository = repository;
    }


}
