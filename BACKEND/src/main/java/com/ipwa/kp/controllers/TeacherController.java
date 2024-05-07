package com.ipwa.kp.controllers;


import com.ipwa.kp.controllers.requests.TeacherPatchRequest;
import com.ipwa.kp.models.Teacher;
import com.ipwa.kp.services.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService service;

    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Teacher> allInPage(@RequestParam Optional<String> sortBy,
                             @RequestParam Optional<Integer> page,
                             @RequestParam Optional<String> direction) {
        return service.getAllTeachersPage(sortBy, page, direction);
    }

    @GetMapping("/nonPage")
    public List<Teacher> all() {
        return service.getAllTeachers();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'STUDENT') or #id == authentication.principal.id")
    public Teacher one(@PathVariable Long id) {
        return service.getOneById(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('COORDINATOR') or #id == authentication.principal.id and hasAuthority('TEACHER')")
    public ResponseEntity<?> updateTeacher(@PathVariable Long id, @RequestBody TeacherPatchRequest request) {
        return service.updateTeacher(id, request);
    }

    @PatchMapping("/{groupId}/{teacherId}")
    @PreAuthorize("hasAuthority('COORDINATOR')")
    public ResponseEntity<?> addTeacherToGroup(@PathVariable Long groupId, @PathVariable Long teacherId) {
        return service.addTeacherToGroup(groupId, teacherId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('COORDINATOR')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.deleteTeacherById(id);
    }
}
