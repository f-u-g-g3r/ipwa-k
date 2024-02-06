package com.ipwa.kp.controllers;

import com.ipwa.kp.repositories.InternshipCoordinatorRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internship-coordinators")
public class InternshipCoordinatorController {
    private final InternshipCoordinatorRepository repository;

    public InternshipCoordinatorController(InternshipCoordinatorRepository repository) {
        this.repository = repository;
    }
}
