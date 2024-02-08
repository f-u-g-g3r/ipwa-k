package com.ipwa.kp.repositories;

import com.ipwa.kp.models.InternshipCoordinator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InternshipCoordinatorRepository extends JpaRepository<InternshipCoordinator, Long> {
    Optional<InternshipCoordinator> findByEmail(String email);
}
