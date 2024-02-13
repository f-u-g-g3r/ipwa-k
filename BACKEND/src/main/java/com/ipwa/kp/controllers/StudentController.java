package com.ipwa.kp.controllers;


import com.ipwa.kp.controllers.exceptions.PostNotFoundException;
import com.ipwa.kp.controllers.exceptions.StudentNotFoundException;
import com.ipwa.kp.models.Post;
import com.ipwa.kp.models.Student;
import com.ipwa.kp.repositories.PostRepository;
import com.ipwa.kp.repositories.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository repository;
    private final PostRepository postRepository;

    public StudentController(StudentRepository repository, PostRepository postRepository) {
        this.repository = repository;
        this.postRepository = postRepository;
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    public List<Student> all() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "*")
    public Student one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @GetMapping("/posts/{postId}")
    @CrossOrigin(origins = "*")
    public List<Student> allStudentsByPostId(@PathVariable Long postId) {
        return repository.findAllByPostsId(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    @PatchMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        fields.forEach((fieldName, value) -> {
            try {
                Field field = Student.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                if (field.getType().isEnum() && value instanceof String) {
                    Enum<?> enumValue = Enum.valueOf((Class<Enum>) field.getType(), (String) value);
                    field.set(student, enumValue);
                } else {
                    field.set(student, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        });

        return ResponseEntity.ok(repository.save(student));
    }

    @PatchMapping("/apply/{studentId}/{postId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> applyFor(@PathVariable Long studentId, @PathVariable Long postId) {
        Student student = repository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        List<Long> studentPosts = student.getPosts();
        List<Post> posts = new ArrayList<>();
        for (Long id : studentPosts) {
            posts.add(postRepository.findById(id)
                    .orElse(null));
        }
        if (posts.contains(post)) {
            posts.remove(post);
        } else {
            posts.add(post);
        }
        student.setPosts(posts);
        repository.save(student);


        List<Long> postStudents = post.getStudents();
        List<Student> students = new ArrayList<>();
        for (Long id : postStudents) {
            students.add(repository.findById(id)
                    .orElse(null));
        }
        if (students.contains(student)) {
            students.remove(student);
        } else {
            students.add(student);
        }
        post.setStudents(students);
        postRepository.save(post);


        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<?> newStudent(@RequestBody Student student) {
        return ResponseEntity.ok(repository.save(student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("ok");
    }
}
