package com.ipwa.kp.controllers;

import com.ipwa.kp.repositories.InternshipCoordinatorRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.ipwa.kp.models.InternshipCoordinator;
import com.ipwa.kp.controllers.exceptions.CoordinatorNotFoundException;

@RestController
@RequestMapping("/internship-coordinators")
public class InternshipCoordinatorController {
    private final InternshipCoordinatorRepository repository;

    public InternshipCoordinatorController(InternshipCoordinatorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "*")
    public InternshipCoordinator one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CoordinatorNotFoundException(id));
    }
}
