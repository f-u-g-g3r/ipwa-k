package com.ipwa.kp.repositories;

import com.ipwa.kp.models.ClassGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassGroupRepository extends JpaRepository<ClassGroup, Long> {
}
