package com.ipwa.kp.services;

import com.ipwa.kp.controllers.exceptions.ResumeNotFoundException;
import com.ipwa.kp.models.Resume;
import com.ipwa.kp.repositories.ResumeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ResumeService {
    private final ResumeRepository repository;

    public ResumeService(ResumeRepository repository) {
        this.repository = repository;
    }

    public List<Resume> all() {
        return repository.findAll();
    }

    public Resume one(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResumeNotFoundException(id));
    }

    public ResponseEntity<?> newResume(Resume resume) {
        return ResponseEntity.ok(repository.save(resume));
    }

    public ResponseEntity<?> deleteById(Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("ok");
    }

}

