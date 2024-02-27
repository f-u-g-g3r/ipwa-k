package com.ipwa.kp.controllers;

import com.ipwa.kp.controllers.exceptions.CompanyNotFoundException;
import com.ipwa.kp.controllers.exceptions.PostNotFoundException;
import com.ipwa.kp.models.Company;
import com.ipwa.kp.models.Post;
import com.ipwa.kp.models.Student;
import com.ipwa.kp.repositories.CompanyRepository;
import com.ipwa.kp.repositories.PostRepository;
import com.ipwa.kp.repositories.StudentRepository;
import com.ipwa.kp.security.config.JwtService;
import com.ipwa.kp.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostRepository repository;
    private final CompanyRepository companyRepository;
    private final StudentRepository studentRepository;
    private final JwtService jwtService;
    private final FileService fileService;

    public PostController(PostRepository repository, CompanyRepository companyRepository, StudentRepository studentRepository, JwtService jwtService, FileService fileService) {
        this.repository = repository;
        this.companyRepository = companyRepository;
        this.studentRepository = studentRepository;
        this.jwtService = jwtService;
        this.fileService = fileService;
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

    @GetMapping("/company/{companyId}")
    public List<Post> allByOwnerId(@PathVariable Long companyId) {
        return repository.findAllByCompanyId(companyId)
                .orElseThrow(() -> new PostNotFoundException(companyId));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'TEACHER') or hasAuthority('STUDENT') and #studentId == authentication.principal.id")
    public List<Post> allByStudentId(@PathVariable Long studentId) {
        return repository.findAllByPostsStudentsStudentId(studentId)
                .orElseThrow(() -> new PostNotFoundException(studentId));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'COMPANY')")
    public ResponseEntity<?> newPost(@RequestHeader(name = "Authorization") String token, @RequestBody Post post) {
        String jwt = token.substring(7);
        Long companyId = jwtService.extractId(jwt);
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(companyId));
        post.setCompany(company);
        return ResponseEntity.ok(repository.save(post));
    }

    @PatchMapping("/{postId}")
    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'COMPANY')")
    public ResponseEntity<?> editPost(@PathVariable Long postId, @RequestBody Post request) {
        Post post = repository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        if (request.getWorkName() != null) post.setWorkName(request.getWorkName());
        if (request.getWorkDescription() != null) post.setWorkDescription(request.getWorkDescription());
        if (request.getSalary() != null) post.setSalary(request.getSalary());
        if (request.getClaims() != null) post.setClaims(request.getClaims());
        if (request.getAdditionalInfo() != null) post.setAdditionalInfo(request.getAdditionalInfo());
        if (request.getExpiryDate() != null) post.setExpiryDate(request.getExpiryDate());

        return ResponseEntity.ok(repository.save(post));
    }

    @PutMapping("/pdf/{id}")
    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'COMPANY')")
    public String uploadPdf(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        String pdfUrl = fileService.uploadPdf.apply(file, "posts");
        post.setPathToPdf(pdfUrl);
        repository.save(post);
        return pdfUrl;
    }

    @GetMapping("/pdf/{fileName}")
    public ResponseEntity<ByteArrayResource> getPdf(@PathVariable String fileName) throws IOException {

        Path absolutePath = Paths.get("").toAbsolutePath();
        Path fileStorageLocation = Paths.get(absolutePath + "/uploads/posts").toAbsolutePath().normalize();
        Path filePath = fileStorageLocation.resolve(fileName).normalize();

        Path pdfPath = Paths.get(filePath.toUri());
        byte[] pdfContent = Files.readAllBytes(pdfPath);

        ByteArrayResource resource = new ByteArrayResource(pdfContent);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=your-pdf-file.pdf");

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfContent.length)
                .body(resource);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('COMPANY', 'COORDINATOR')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("ok");
    }
}
