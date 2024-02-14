package com.ipwa.kp.controllers;


import com.ipwa.kp.controllers.exceptions.ClassGroupNotFoundException;
import com.ipwa.kp.controllers.exceptions.StudentNotFoundException;
import com.ipwa.kp.controllers.exceptions.TeacherNotFoundException;
import com.ipwa.kp.models.ClassGroup;
import com.ipwa.kp.models.Student;
import com.ipwa.kp.models.Teacher;
import com.ipwa.kp.repositories.ClassGroupRepository;
import com.ipwa.kp.repositories.TeacherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherRepository repository;
    private final ClassGroupRepository classGroupRepository;

    public TeacherController(TeacherRepository repository, ClassGroupRepository classGroupRepository) {
        this.repository = repository;
        this.classGroupRepository = classGroupRepository;
    }

    @GetMapping
    public List<Teacher> all() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "*")
    public Teacher one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<?> newTeacher(@RequestBody Teacher teacher) {
        return ResponseEntity.ok(repository.save(teacher));
    }

    @PatchMapping("/{groupId}/{teacherId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> addTeacherToGroup(@PathVariable Long groupId, @PathVariable Long teacherId) {
        Teacher teacher = repository.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException(teacherId));
        ClassGroup group = classGroupRepository.findById(groupId)
                .orElseThrow(() -> new ClassGroupNotFoundException(groupId));

        List<Teacher> teachers = group.getTeachers();
        teachers.add(teacher);
        group.setTeachers(teachers);
        classGroupRepository.save(group);

        teacher.setClassGroup(group);

        return ResponseEntity.ok(repository.save(teacher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("ok");
    }
}
