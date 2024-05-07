package com.ipwa.kp.services;

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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository repository;
    private final ClassGroupRepository classGroupRepository;
    private final AuthenticationService authenticationService;

    public TeacherService(TeacherRepository repository, ClassGroupRepository classGroupRepository, AuthenticationService authenticationService) {
        this.repository = repository;
        this.classGroupRepository = classGroupRepository;
        this.authenticationService = authenticationService;
    }

    public Page<Teacher> getAllTeachersPage(Optional<String> sortBy, Optional<Integer> page, Optional<String> direction) {
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

    public List<Teacher> getAllTeachers() {
        return repository.findAll();
    }

    public Teacher getOneById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));
    }

    public ResponseEntity<?> updateTeacher(Long id, TeacherPatchRequest request) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));

        if (request.getEmail() != null && authenticationService.isEmailNotTaken(request.getEmail()) && authenticationService.isUsernameNotTaken(request.getUsername())) teacher.setEmail(request.getEmail());
        if (request.getUsername() != null && authenticationService.isEmailNotTaken(request.getEmail()) && authenticationService.isUsernameNotTaken(request.getUsername())) teacher.setUsername(request.getUsername());
        if (request.getFirstName() != null) teacher.setFirstName(request.getFirstName());
        if (request.getLastName() != null) teacher.setLastName(request.getLastName());

        return ResponseEntity.ok(repository.save(teacher));
    }

    public ResponseEntity<?> addTeacherToGroup(Long groupId, Long teacherId) {
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

    public ResponseEntity<?> deleteTeacherById(Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("ok");
    }
}
