package com.ipwa.kp.controllers;

import com.ipwa.kp.controllers.exceptions.ClassGroupNotFoundException;
import com.ipwa.kp.models.ClassGroup;
import com.ipwa.kp.models.Student;
import com.ipwa.kp.models.Teacher;
import com.ipwa.kp.repositories.ClassGroupRepository;
import com.ipwa.kp.repositories.StudentRepository;
import com.ipwa.kp.repositories.TeacherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/class-groups")
@RestController
public class ClassGroupController {
    private final ClassGroupRepository repository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public ClassGroupController(ClassGroupRepository repository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    public List<ClassGroup> all() {
        return repository.findAll();
    }

    @PostMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> createClassGroup(@RequestBody ClassGroup classGroup) {
        return ResponseEntity.ok(repository.save(classGroup));
    }

    @GetMapping("/{name}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> getClassGroupByName(@PathVariable String name) {
        ClassGroup group = repository.findByName(name)
                .orElseThrow(() -> new ClassGroupNotFoundException(name));
        return ResponseEntity.ok(group);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "*")
    public void deleteGroup(@PathVariable Long id) {
        List<Student> students = studentRepository.findAllByClassGroupId(id)
                .orElseThrow(() -> new ClassGroupNotFoundException(id));

        Teacher teacher = teacherRepository.findByClassGroupId(id)
                .orElseThrow(() -> new ClassGroupNotFoundException(id));

        for (Student student : students) {
            student.setClassGroup(null);
        }

        teacher.setClassGroup(null);

        repository.deleteById(id);
    }

}
