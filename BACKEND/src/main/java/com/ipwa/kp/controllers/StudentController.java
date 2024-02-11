package com.ipwa.kp.controllers;


import com.ipwa.kp.controllers.exceptions.StudentNotFoundException;
import com.ipwa.kp.models.Student;
import com.ipwa.kp.repositories.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Student> all() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "*")
    public Student one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @PatchMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Student.class, (String) key);
            field.setAccessible (true);
            ReflectionUtils.setField(field, student, value);
        });
        return ResponseEntity.ok(repository.save(student));
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
