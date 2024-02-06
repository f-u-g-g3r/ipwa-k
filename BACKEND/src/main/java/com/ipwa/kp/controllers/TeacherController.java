package com.ipwa.kp.controllers;


import com.ipwa.kp.repositories.TeacherRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherRepository repository;

    public TeacherController(TeacherRepository repository) {
        this.repository = repository;
    }
}
