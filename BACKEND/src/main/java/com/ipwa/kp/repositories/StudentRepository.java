package com.ipwa.kp.repositories;

import com.ipwa.kp.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Page<Student>> findAllByClassGroupName(String classGroup, PageRequest pageRequest);
    Optional<List<Student>> findAllByClassGroupId(Long classGroupId);
    Optional<Student> findByEmail(String email);
    Optional<Student> findByUsername(String username);
    Optional<List<Student>> findAllByPostsStudentsPostId(Long postsStudentsId);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
