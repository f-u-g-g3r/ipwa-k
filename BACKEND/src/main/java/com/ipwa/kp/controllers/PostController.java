package com.ipwa.kp.controllers;

import com.ipwa.kp.models.Post;
import com.ipwa.kp.repositories.PostRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostRepository repository;

    public PostController(PostRepository repository) {
        this.repository = repository;
    }

}
