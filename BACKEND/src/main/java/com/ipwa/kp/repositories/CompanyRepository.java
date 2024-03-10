package com.ipwa.kp.repositories;

import com.ipwa.kp.models.Company;
import com.ipwa.kp.models.Post;
import com.ipwa.kp.models.PostStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByEmail(String email);
    Optional<Company> findByUsername(String username);
    Optional<List<Company>> findByPostsPostsStudentsStudentId(Long studentId);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);

}
