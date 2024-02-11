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
