package com.ipwa.kp.services;


import com.ipwa.kp.controllers.exceptions.CompanyNotFoundException;
import com.ipwa.kp.controllers.exceptions.PostNotFoundException;
import com.ipwa.kp.models.Company;
import com.ipwa.kp.models.Post;
import com.ipwa.kp.repositories.CompanyRepository;
import com.ipwa.kp.repositories.PostRepository;
import com.ipwa.kp.security.config.JwtService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {


    private final PostRepository repository;
    private final CompanyRepository companyRepository;
    private final JwtService jwtService;
    private final FileService fileService;

    public PostService(PostRepository repository, CompanyRepository companyRepository, JwtService jwtService, FileService fileService) {
        this.repository = repository;
        this.companyRepository = companyRepository;
        this.jwtService = jwtService;
        this.fileService = fileService;
    }

    public Page<Post> getAllPostsPage(Optional<String> sortBy, Optional<Integer> page, Optional<String> direction) {
        Sort.Direction sort = Sort.Direction.ASC;
        if (direction.isPresent() && direction.get().equals("DESC")) {
            sort = Sort.Direction.DESC;
        }
        return repository.findAll(PageRequest.of(
                page.orElse(0),
                5,
                sort, sortBy.orElse("id")
        ));
    }

    public Post getOneById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    public List<Post> allByOwnerId(Long companyId) {
        return repository.findAllByCompanyId(companyId)
                .orElseThrow(() -> new PostNotFoundException(companyId));
    }

    public List<Post> allByStudentId(Long studentId) {
        return repository.findAllByPostsStudentsStudentId(studentId)
                .orElseThrow(() -> new PostNotFoundException(studentId));
    }

    public ResponseEntity<?> createPost(String token, Post post) {
        String jwt = token.substring(7);
        Long companyId = jwtService.extractId(jwt);
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(companyId));
        post.setCompany(company);
        return ResponseEntity.ok(repository.save(post));
    }

    public ResponseEntity<?> editPost(Long postId, Post request) {
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

    public String uploadPdf(Long id, MultipartFile file) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        String pdfUrl = fileService.uploadPdf.apply(file, "posts");
        post.setPathToPdf(pdfUrl);
        repository.save(post);
        return pdfUrl;
    }

    public ResponseEntity<ByteArrayResource> getPdf(String fileName) throws IOException {

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

    public ResponseEntity<?> deleteById(Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("ok");
    }

}
