package com.ipwa.kp.controllers;


import com.ipwa.kp.controllers.exceptions.ClassGroupNotFoundException;
import com.ipwa.kp.controllers.exceptions.TeacherNotFoundException;
import com.ipwa.kp.controllers.requests.TeacherPatchRequest;
import com.ipwa.kp.models.ClassGroup;
import com.ipwa.kp.models.Teacher;
import com.ipwa.kp.repositories.ClassGroupRepository;
import com.ipwa.kp.repositories.TeacherRepository;
import com.ipwa.kp.security.auth.AuthenticationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherRepository repository;
    private final ClassGroupRepository classGroupRepository;
    private final AuthenticationService authenticationService;

    public TeacherController(TeacherRepository repository, ClassGroupRepository classGroupRepository, AuthenticationService authenticationService) {
        this.repository = repository;
        this.classGroupRepository = classGroupRepository;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public Page<Teacher> allInPage(@RequestParam Optional<String> sortBy,
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

    @GetMapping("/nonPage")
    public List<Teacher> all() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'STUDENT') or #id == authentication.principal.id")
    public Teacher one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('COORDINATOR') or #id == authentication.principal.id and hasAuthority('TEACHER')")
    public ResponseEntity<?> updateTeacher(@PathVariable Long id, @RequestBody TeacherPatchRequest request) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));

        if (request.getEmail() != null && authenticationService.isEmailNotTaken(request.getEmail()) && authenticationService.isUsernameNotTaken(request.getUsername())) teacher.setEmail(request.getEmail());
        if (request.getUsername() != null && authenticationService.isEmailNotTaken(request.getEmail()) && authenticationService.isUsernameNotTaken(request.getUsername())) teacher.setUsername(request.getUsername());
        if (request.getFirstName() != null) teacher.setFirstName(request.getFirstName());
        if (request.getLastName() != null) teacher.setLastName(request.getLastName());

        return ResponseEntity.ok(repository.save(teacher));
    }

    @PatchMapping("/{groupId}/{teacherId}")
    @PreAuthorize("hasAuthority('COORDINATOR')")
    public ResponseEntity<?> addTeacherToGroup(@PathVariable Long groupId, @PathVariable Long teacherId) {
        ClassGroup group = classGroupRepository.findById(groupId)
                .orElseThrow(() -> new ClassGroupNotFoundException(groupId));

        if (teacherId == -1) {
            Teacher teacher = repository.findById(group.getTeacher())
                    .orElseThrow(() -> new TeacherNotFoundException(teacherId));
            group.setTeacher(null);
            classGroupRepository.save(group);
            teacher.setClassGroup(null);
            return ResponseEntity.ok(repository.save(teacher));
        } else {
            Teacher teacher = repository.findById(teacherId)
                    .orElseThrow(() -> new TeacherNotFoundException(teacherId));
            group.setTeacher(teacher);
            classGroupRepository.save(group);
            teacher.setClassGroup(group);
            return ResponseEntity.ok(repository.save(teacher));
        }
    }

//    @PatchMapping("/{groupId}/{teacherId}")
//    @PreAuthorize("hasAuthority('COORDINATOR')")
//    public ResponseEntity<?> removeTeacherFromGroup(@PathVariable Long groupId, @PathVariable Long teacherId) {
//        Teacher teacher = repository.findById(teacherId)
//                .orElseThrow(() -> new TeacherNotFoundException(teacherId));
//        ClassGroup group = classGroupRepository.findById(groupId)
//                .orElseThrow(() -> new ClassGroupNotFoundException(groupId));
//        group.setTeacher(null);
//        classGroupRepository.save(group);
//        teacher.setClassGroup(null);
//        return ResponseEntity.ok(repository.save(teacher));
//    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('COORDINATOR')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("ok");
    }
}
