package com.ipwa.kp.controllers;


import com.ipwa.kp.controllers.exceptions.ClassGroupNotFoundException;
import com.ipwa.kp.controllers.exceptions.PostNotFoundException;
import com.ipwa.kp.controllers.exceptions.ResumeNotFoundException;
import com.ipwa.kp.controllers.exceptions.StudentNotFoundException;
import com.ipwa.kp.controllers.requests.StudentPatchRequest;
import com.ipwa.kp.models.ClassGroup;
import com.ipwa.kp.models.Post;
import com.ipwa.kp.models.Resume;
import com.ipwa.kp.models.Student;
import com.ipwa.kp.repositories.ClassGroupRepository;
import com.ipwa.kp.repositories.PostRepository;
import com.ipwa.kp.repositories.ResumeRepository;
import com.ipwa.kp.repositories.StudentRepository;
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
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository repository;
    private final PostRepository postRepository;
    private final ClassGroupRepository classGroupRepository;
    private final FileService fileService;
    private final ResumeRepository resumeRepository;

    public StudentController(StudentRepository repository, PostRepository postRepository, ClassGroupRepository classGroupRepository, FileService fileService, ResumeRepository resumeRepository) {
        this.repository = repository;
        this.postRepository = postRepository;
        this.classGroupRepository = classGroupRepository;
        this.fileService = fileService;
        this.resumeRepository = resumeRepository;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('COORDINATOR')")
    @CrossOrigin(origins = "*")
    public List<Student> all() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('COMPANY', 'TEACHER', 'COORDINATOR') or #id == authentication.principal.id")
    @CrossOrigin(origins = "*")
    public Student one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @GetMapping("/posts/{postId}")
    @PreAuthorize("hasAnyAuthority('COMPANY', 'TEACHER', 'COORDINATOR')")
    @CrossOrigin(origins = "*")
    public List<Student> allStudentsByPostId(@PathVariable Long postId) {
        return repository.findAllByPostsId(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('COORDINATOR') or hasAuthority('STUDENT') and #id == authentication.principal.id")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody StudentPatchRequest request) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        if (request.getUsername() != null) student.setUsername(request.getUsername());
        if (request.getFirstName() != null) student.setFirstName(request.getFirstName());
        if (request.getLastName() != null) student.setLastName(request.getLastName());
        if (request.getEmail() != null) student.setEmail(request.getEmail());

        return ResponseEntity.ok(repository.save(student));
    }

    @PatchMapping("/apply/{studentId}/{postId}")
    @PreAuthorize("hasAuthority('STUDENT') and #studentId == authentication.principal.id")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> applyFor(@PathVariable Long studentId, @PathVariable Long postId) {
        Student student = repository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        List<Long> studentPosts = student.getPosts();
        List<Post> posts = new ArrayList<>();
        for (Long id : studentPosts) {
            posts.add(postRepository.findById(id)
                    .orElse(null));
        }
        if (posts.contains(post)) {
            posts.remove(post);
        } else {
            posts.add(post);
        }
        student.setPosts(posts);
        repository.save(student);


        List<Long> postStudents = post.getStudents();
        List<Student> students = new ArrayList<>();
        for (Long id : postStudents) {
            students.add(repository.findById(id)
                    .orElse(null));
        }
        if (students.contains(student)) {
            students.remove(student);
        } else {
            students.add(student);
        }
        post.setStudents(students);
        postRepository.save(post);


        return ResponseEntity.ok(post);
    }

//    @PostMapping
//    public ResponseEntity<?> newStudent(@RequestBody Student student) {
//        return ResponseEntity.ok(repository.save(student));
//    }

    @PatchMapping("/{groupId}/{studentId}")
    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'TEACHER')")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> addStudentToGroup(@PathVariable Long groupId, @PathVariable Long studentId) {
        Student student = repository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
        ClassGroup group = classGroupRepository.findById(groupId)
                .orElseThrow(() -> new ClassGroupNotFoundException(groupId));

        List<Student> students = group.getStudents();
        students.add(student);
        group.setStudents(students);
        classGroupRepository.save(group);

        student.setClassGroup(group);

        return ResponseEntity.ok(repository.save(student));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('COORDINATOR')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/{id}/cv")
    @PreAuthorize("hasAuthority('STUDENT') and #id == authentication.principal.id")
    @CrossOrigin(origins = "*")
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

    @GetMapping("/test/{id}")
    @PreAuthorize("hasAuthority('STUDENT') and #id == authentication.principal.id")
    public String test(@PathVariable Long id) {
        return "123";
    }

    @PutMapping("/{id}/motivationLetter")
    @PreAuthorize("hasAuthority('STUDENT') and #id == authentication.principal.id")
    @CrossOrigin(origins = "*")
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
    @CrossOrigin(origins = "*")
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
    @CrossOrigin(origins = "*")
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
