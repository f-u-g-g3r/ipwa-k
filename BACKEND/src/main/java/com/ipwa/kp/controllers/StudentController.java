package com.ipwa.kp.controllers;


import com.ipwa.kp.controllers.exceptions.ClassGroupNotFoundException;
import com.ipwa.kp.controllers.exceptions.PostNotFoundException;
import com.ipwa.kp.controllers.exceptions.ResumeNotFoundException;
import com.ipwa.kp.controllers.exceptions.StudentNotFoundException;
import com.ipwa.kp.controllers.requests.StudentPatchRequest;
import com.ipwa.kp.models.*;
import com.ipwa.kp.repositories.*;
import com.ipwa.kp.security.auth.AuthenticationService;
import com.ipwa.kp.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository repository;
    private final PostRepository postRepository;
    private final ClassGroupRepository classGroupRepository;
    private final FileService fileService;
    private final ResumeRepository resumeRepository;
    private final PostStudentRepository postStudentRepository;
    private final AuthenticationService authenticationService;

    public StudentController(StudentRepository repository, PostRepository postRepository, ClassGroupRepository classGroupRepository, FileService fileService, ResumeRepository resumeRepository, PostStudentRepository postStudentRepository, AuthenticationService authenticationService) {
        this.repository = repository;
        this.postRepository = postRepository;
        this.classGroupRepository = classGroupRepository;
        this.fileService = fileService;
        this.resumeRepository = resumeRepository;
        this.postStudentRepository = postStudentRepository;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('COORDINATOR')")
    public Page<Student> all(@RequestParam Optional<String> sortBy,
                             @RequestParam Optional<Integer> page,
                             @RequestParam Optional<String> direction) {
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

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('COMPANY', 'TEACHER', 'COORDINATOR') or #id == authentication.principal.id")
    public Student one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @GetMapping("/posts/{postId}")
    @PreAuthorize("hasAnyAuthority('COMPANY', 'TEACHER', 'COORDINATOR')")
    public List<Student> allStudentsByPostId(@PathVariable Long postId) {
        return repository.findAllByPostsStudentsPostId(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('COORDINATOR') or hasAuthority('STUDENT') and #id == authentication.principal.id")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody StudentPatchRequest request) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        if (request.getUsername() != null && authenticationService.isEmailNotTaken(request.getEmail()) && authenticationService.isUsernameNotTaken(request.getUsername())) student.setUsername(request.getUsername());
        if (request.getFirstName() != null) student.setFirstName(request.getFirstName());
        if (request.getLastName() != null) student.setLastName(request.getLastName());
        if (request.getEmail() != null  && authenticationService.isEmailNotTaken(request.getEmail()) && authenticationService.isUsernameNotTaken(request.getUsername())) student.setEmail(request.getEmail());
        if (request.getAccountStatus() != null) student.setAccountStatus(request.getAccountStatus());

        return ResponseEntity.ok(repository.save(student));
    }

    @PatchMapping("/apply/{studentId}/{postId}")
    @PreAuthorize("hasAuthority('STUDENT') and #studentId == authentication.principal.id")
    public ResponseEntity<?> applyFor(@PathVariable Long studentId, @PathVariable Long postId) {
        Student student = repository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        PostStudent postStudent = postStudentRepository.findByPostAndStudent(post, student)
                .orElseGet(() -> new PostStudent(post, student));

        if (postStudent.getId() == null) {
            postStudentRepository.save(postStudent);
        } else {
            postStudentRepository.delete(postStudent);
        }

        return ResponseEntity.ok(post);
    }

    @PatchMapping("/{groupId}/{studentId}")
    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'TEACHER')")
    public ResponseEntity<?> addStudentToGroup(@PathVariable Long groupId, @PathVariable Long studentId) {
        Student student = repository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        if (groupId == -1) {
            ClassGroup group = classGroupRepository.findByName(student.getClassGroup())
                    .orElseThrow(() -> new ClassGroupNotFoundException(groupId));
            List<Student> students = group.getStudents();
            students.remove(student);
            group.setStudents(students);
            classGroupRepository.save(group);
            student.setClassGroup(null);
            return ResponseEntity.ok(repository.save(student));
        } else {
            ClassGroup group = classGroupRepository.findById(groupId)
                    .orElseThrow(() -> new ClassGroupNotFoundException(groupId));

            List<Student> students = group.getStudents();
            students.add(student);
            group.setStudents(students);
            classGroupRepository.save(group);
            student.setClassGroup(group);
            return ResponseEntity.ok(repository.save(student));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('COORDINATOR')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/{id}/cv")
    @PreAuthorize("hasAuthority('STUDENT') and #id == authentication.principal.id")
    public String uploadCv(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        String cvUrl = fileService.uploadPdf.apply(file, "cvs");
        Resume resume = resumeRepository.findById(student.getResume())
                        .orElseThrow(() -> new ResumeNotFoundException(student.getResume()));
        resume.setCv(cvUrl);
        student.setResume(resume);

        repository.save(student);
        resumeRepository.save(resume);
        return cvUrl;
    }

    @PutMapping("/{id}/motivationLetter")
    @PreAuthorize("hasAuthority('STUDENT') and #id == authentication.principal.id")
    public String uploadMotivationLetter(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        String motivationLetterUrl = fileService.uploadPdf.apply(file, "motivationLetters");
        Resume resume = resumeRepository.findById(student.getResume())
                        .orElseThrow(() -> new ResumeNotFoundException(student.getResume()));
        resume.setMotivationLetter(motivationLetterUrl);
        student.setResume(resume);

        repository.save(student);
        resumeRepository.save(resume);
        return motivationLetterUrl;
    }


    // to improve: get cv/letter by studentId, not by fileName
    @GetMapping("/cv/{fileName}")
    public ResponseEntity<ByteArrayResource> getCv(@PathVariable String fileName) throws IOException {
        Path absolutePath = Paths.get("").toAbsolutePath();
        Path fileStorageLocation = Paths.get(absolutePath+"/uploads/cvs").toAbsolutePath().normalize();
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

    @GetMapping("/motivationLetter/{fileName}")
    public ResponseEntity<ByteArrayResource> getMotivationLetter(@PathVariable String fileName) throws IOException {
        Path absolutePath = Paths.get("").toAbsolutePath();
        Path fileStorageLocation = Paths.get(absolutePath+"/uploads/motivationLetters").toAbsolutePath().normalize();
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
}
