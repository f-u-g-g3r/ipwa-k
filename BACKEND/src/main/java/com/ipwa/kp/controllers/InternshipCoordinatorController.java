package com.ipwa.kp.controllers;

import com.ipwa.kp.services.InternshipCoordinatorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.ipwa.kp.models.InternshipCoordinator;

@RestController
@RequestMapping("/internship-coordinators")
public class InternshipCoordinatorController {
    private final InternshipCoordinatorService service;

    public InternshipCoordinatorController(InternshipCoordinatorService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('COORDINATOR')")
    public InternshipCoordinator one(@PathVariable Long id) {
        return service.getOneById(id);
    }
}
