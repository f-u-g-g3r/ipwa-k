package com.ipwa.kp.repositories;

import com.ipwa.kp.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByEmail(String email);
    Optional<Company> findByUsername(String username);

}
