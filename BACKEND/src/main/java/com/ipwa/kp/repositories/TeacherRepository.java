package com.ipwa.kp.repositories;

import com.ipwa.kp.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEmail(String email);
    Optional<Teacher> findByUsername(String username);

    Optional<List<Teacher>> findAllByClassGroupId(Long classGroupId);
}
