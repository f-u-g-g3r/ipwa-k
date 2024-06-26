package com.ipwa.kp.repositories;

import com.ipwa.kp.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<List<Post>> findAllByCompanyId(Long companyId);
    Optional<List<Post>> findAllByPostsStudentsStudentId(Long studentId);
}
