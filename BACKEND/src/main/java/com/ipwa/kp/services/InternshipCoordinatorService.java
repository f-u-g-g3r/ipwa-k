package com.ipwa.kp.services;

import com.ipwa.kp.controllers.exceptions.CoordinatorNotFoundException;
import com.ipwa.kp.models.InternshipCoordinator;
import com.ipwa.kp.repositories.InternshipCoordinatorRepository;
import org.springframework.stereotype.Service;

@Service
public class InternshipCoordinatorService {

    private final InternshipCoordinatorRepository repository;

    public InternshipCoordinatorService(InternshipCoordinatorRepository repository) {
        this.repository = repository;
    }

    public InternshipCoordinator getOneById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CoordinatorNotFoundException(id));
    }
}
