package com.ipwa.kp.controllers;

import com.ipwa.kp.repositories.ResumeRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resumes")
public class ResumeController {
    private final ResumeRepository repository;

    public ResumeController(ResumeRepository repository) {
        this.repository = repository;
    }
}
