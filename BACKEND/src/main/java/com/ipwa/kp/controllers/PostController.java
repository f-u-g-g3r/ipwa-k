package com.ipwa.kp.controllers;

import com.ipwa.kp.controllers.exceptions.PostNotFoundException;
import com.ipwa.kp.models.Post;
import com.ipwa.kp.repositories.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostRepository repository;

    public PostController(PostRepository repository) {
        this.repository = repository;
    }
    @GetMapping
    public List<Post> all() {
        return repository.findAll();
    }
    @GetMapping("/{id}")
    public Post one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<?> newPost(@RequestBody Post post) {
        return ResponseEntity.ok(repository.save(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("ok");
    }
}
