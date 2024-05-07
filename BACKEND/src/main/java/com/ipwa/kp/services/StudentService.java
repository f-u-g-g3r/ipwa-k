package com.ipwa.kp.services;


import com.ipwa.kp.controllers.exceptions.ClassGroupNotFoundException;
import com.ipwa.kp.controllers.exceptions.PostNotFoundException;
import com.ipwa.kp.controllers.exceptions.ResumeNotFoundException;
import com.ipwa.kp.controllers.exceptions.StudentNotFoundException;
import com.ipwa.kp.controllers.requests.StudentPatchRequest;
import com.ipwa.kp.models.*;
import com.ipwa.kp.repositories.*;
import com.ipwa.kp.security.auth.AuthenticationService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository repository;
    private final PostRepository postRepository;
    private final ClassGroupRepository classGroupRepository;
    private final FileService fileService;
    private final ResumeRepository resumeRepository;
    private final PostStudentRepository postStudentRepository;
    private final AuthenticationService authenticationService;

    public StudentService(StudentRepository repository, PostRepository postRepository, ClassGroupRepository classGroupRepository, FileService fileService, ResumeRepository resumeRepository, PostStudentRepository postStudentRepository, AuthenticationService authenticationService) {
        this.repository = repository;
        this.postRepository = postRepository;
        this.classGroupRepository = classGroupRepository;
        this.fileService = fileService;
        this.resumeRepository = resumeRepository;
        this.postStudentRepository = postStudentRepository;
        this.authenticationService = authenticationService;
    }

    public Page<Student> getAllStudents(Optional<String> sortBy, Optional<Integer> page, Optional<String> direction) {
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

    public Student getOneById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public List<Student> getAllStudentsByPostId(Long postId) {
        return repository.findAllByPostsStudentsPostId(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    public ResponseEntity<?> updateStudent(Long id, StudentPatchRequest request) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        if (request.getUsername() != null && authenticationService.isEmailNotTaken(request.getEmail()) && authenticationService.isUsernameNotTaken(request.getUsername())) student.setUsername(request.getUsername());
        if (request.getFirstName() != null) student.setFirstName(request.getFirstName());
        if (request.getLastName() != null) student.setLastName(request.getLastName());
        if (request.getEmail() != null  && authenticationService.isEmailNotTaken(request.getEmail()) && authenticationService.isUsernameNotTaken(request.getUsername())) student.setEmail(request.getEmail());
        if (request.getAccountStatus() != null) student.setAccountStatus(request.getAccountStatus());

        return ResponseEntity.ok(repository.save(student));
    }

    public ResponseEntity<?> applyFor(Long studentId, Long postId) {
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

    public ResponseEntity<?> addStudentToGroup(Long groupId, Long studentId) {
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

    public ResponseEntity<?> deleteStudentById(Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("ok");
    }

    public String uploadCv(Long id, MultipartFile file) {
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

    public String uploadMotivationLetter(Long id, MultipartFile file) {
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

    // to refactor
    public ResponseEntity<ByteArrayResource> getCv(String fileName) throws IOException {
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

    public ResponseEntity<ByteArrayResource> getMotivationLetter(String fileName) throws IOException {
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
