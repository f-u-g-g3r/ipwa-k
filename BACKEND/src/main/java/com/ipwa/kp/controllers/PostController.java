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

    @PostMapping
    public void add(@RequestBody Post post) {
        repository.save(post);
    }

    @GetMapping
    public List<Post> all() {
        return repository.findAll();
    }
}
