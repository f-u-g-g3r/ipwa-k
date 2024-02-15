package com.ipwa.kp.controllers;

import com.ipwa.kp.controllers.exceptions.ResumeNotFoundException;
import com.ipwa.kp.models.Resume;
import com.ipwa.kp.repositories.ResumeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resumes")
public class ResumeController {
    private final ResumeRepository repository;

    public ResumeController(ResumeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Resume> all() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "*")
    public Resume one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResumeNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<?> newResume(@RequestBody Resume resume) {
        return ResponseEntity.ok(repository.save(resume));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("ok");
    }
}
