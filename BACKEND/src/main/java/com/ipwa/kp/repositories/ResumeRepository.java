package com.ipwa.kp.repositories;

import com.ipwa.kp.models.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
