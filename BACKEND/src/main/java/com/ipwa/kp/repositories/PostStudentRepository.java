package com.ipwa.kp.repositories;

import com.ipwa.kp.models.Post;
import com.ipwa.kp.models.PostStudent;
import com.ipwa.kp.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostStudentRepository extends JpaRepository<PostStudent, Long> {
    Optional<PostStudent> findByPostAndStudent(Post post, Student student);
}
