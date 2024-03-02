package com.ipwa.kp.controllers;

import com.ipwa.kp.controllers.exceptions.ClassGroupNotFoundException;
import com.ipwa.kp.models.ClassGroup;
import com.ipwa.kp.models.Student;
import com.ipwa.kp.models.Teacher;
import com.ipwa.kp.repositories.ClassGroupRepository;
import com.ipwa.kp.repositories.StudentRepository;
import com.ipwa.kp.repositories.TeacherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public List<ClassGroup> all() {
        return repository.findAll();
    }

    @GetMapping("/pages")
    public Page<ClassGroup> allByPage(@RequestParam Optional<String> sortBy,
                                      @RequestParam Optional<Integer> page,
                                      @RequestParam Optional<String> direction) {
        Sort.Direction sort = Sort.Direction.ASC;
        if (direction.isPresent() && direction.get().equals("DESC")) {
            sort = Sort.Direction.DESC;
        }
        return repository.findAll(PageRequest.of(
                page.orElse(0),
                5,
                sort, sortBy.orElse("id")
        ));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('COORDINATOR')")
    public ResponseEntity<?> createClassGroup(@RequestBody ClassGroup classGroup) {
        return ResponseEntity.ok(repository.save(classGroup));
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getClassGroupByName(@PathVariable String name) {
        ClassGroup group = repository.findByName(name)
                .orElseThrow(() -> new ClassGroupNotFoundException(name));
        return ResponseEntity.ok(group);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('COORDINATOR')")
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
