package com.ipwa.kp.controllers;

import com.ipwa.kp.models.Post;
import com.ipwa.kp.services.PostService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Post> all(@RequestParam Optional<String> sortBy,
                          @RequestParam Optional<Integer> page,
                          @RequestParam Optional<String> direction) {
        return service.getAllPostsPage(sortBy, page, direction);
    }

    @GetMapping("/{id}")
    public Post one(@PathVariable Long id) {
        return service.getOneById(id);
    }

    @GetMapping("/company/{companyId}")
    public List<Post> allByOwnerId(@PathVariable Long companyId) {
        return service.allByOwnerId(companyId);
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'TEACHER') or hasAuthority('STUDENT') and #studentId == authentication.principal.id")
    public List<Post> allByStudentId(@PathVariable Long studentId) {
        return service.allByStudentId(studentId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'COMPANY')")
    public ResponseEntity<?> newPost(@RequestHeader(name = "Authorization") String token, @RequestBody Post post) {
       return service.createPost(token, post);
    }

    @PatchMapping("/{postId}")
    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'COMPANY')")
    public ResponseEntity<?> editPost(@PathVariable Long postId, @RequestBody Post request) {
        return service.editPost(postId, request);
    }

    @PutMapping("/pdf/{id}")
    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'COMPANY')")
    public String uploadPdf(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return service.uploadPdf(id, file);
    }

    @GetMapping("/pdf/{fileName}")
    public ResponseEntity<ByteArrayResource> getPdf(@PathVariable String fileName) throws IOException {
        return service.getPdf(fileName);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('COMPANY', 'COORDINATOR')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.deleteById(id);
    }
}
