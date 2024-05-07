package com.ipwa.kp.controllers;

import com.ipwa.kp.models.Resume;
import com.ipwa.kp.services.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resumes")
public class ResumeController {
    private final ResumeService service;

    public ResumeController(ResumeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Resume> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public Resume one(@PathVariable Long id) {
        return service.one(id);
    }

    @PostMapping
    public ResponseEntity<?> newResume(@RequestBody Resume resume) {
        return service.newResume(resume);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('COORDINATOR')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.deleteById(id);
    }
}
