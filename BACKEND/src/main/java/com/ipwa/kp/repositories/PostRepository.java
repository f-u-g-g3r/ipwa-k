package com.ipwa.kp.repositories;

import com.ipwa.kp.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
