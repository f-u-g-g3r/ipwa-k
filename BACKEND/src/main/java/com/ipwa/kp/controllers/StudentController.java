package com.ipwa.kp.controllers;

import com.ipwa.kp.controllers.requests.StudentPatchRequest;
import com.ipwa.kp.models.*;
import com.ipwa.kp.services.StudentService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('COORDINATOR')")
    public Page<Student> all(@RequestParam Optional<String> sortBy,
                             @RequestParam Optional<Integer> page,
                             @RequestParam Optional<String> direction) {
        return service.getAllStudents(sortBy, page, direction);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('COMPANY', 'TEACHER', 'COORDINATOR') or #id == authentication.principal.id")
    public Student one(@PathVariable Long id) {
        return service.getOneById(id);
    }

    @GetMapping("/posts/{postId}")
    @PreAuthorize("hasAnyAuthority('COMPANY', 'TEACHER', 'COORDINATOR')")
    public List<Student> allStudentsByPostId(@PathVariable Long postId) {
        return service.getAllStudentsByPostId(postId)
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('COORDINATOR') or hasAuthority('STUDENT') and #id == authentication.principal.id")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody StudentPatchRequest request) {
        return service.updateStudent(id, request);
    }

    @PatchMapping("/apply/{studentId}/{postId}")
    @PreAuthorize("hasAuthority('STUDENT') and #studentId == authentication.principal.id")
    public ResponseEntity<?> applyFor(@PathVariable Long studentId, @PathVariable Long postId) {
        return service.applyFor(studentId, postId);
    }

    @PatchMapping("/{groupId}/{studentId}")
    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'TEACHER')")
    public ResponseEntity<?> addStudentToGroup(@PathVariable Long groupId, @PathVariable Long studentId) {
        return service.addStudentToGroup(groupId, studentId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('COORDINATOR')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.deleteStudentById(id);
    }

    @PutMapping("/{id}/cv")
    @PreAuthorize("hasAuthority('STUDENT') and #id == authentication.principal.id")
    public String uploadCv(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return service.uploadCv(id, file);
    }

    @PutMapping("/{id}/motivationLetter")
    @PreAuthorize("hasAuthority('STUDENT') and #id == authentication.principal.id")
    public String uploadMotivationLetter(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return service.uploadMotivationLetter(id, file);
    }


    // to improve: get cv/letter by studentId, not by fileName
    @GetMapping("/cv/{fileName}")
    public ResponseEntity<ByteArrayResource> getCv(@PathVariable String fileName) throws IOException {
        return service.getCv(fileName);
    }

    @GetMapping("/motivationLetter/{fileName}")
    public ResponseEntity<ByteArrayResource> getMotivationLetter(@PathVariable String fileName) throws IOException {
        return service.getMotivationLetter(fileName);
    }
}
