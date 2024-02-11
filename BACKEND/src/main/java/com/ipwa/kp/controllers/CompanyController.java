package com.ipwa.kp.controllers;

import com.ipwa.kp.controllers.exceptions.CompanyNotFoundException;
import com.ipwa.kp.models.Company;
import com.ipwa.kp.repositories.CompanyRepository;
import com.ipwa.kp.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyRepository repository;
    private final FileService fileService;


    public CompanyController(CompanyRepository repository, FileService fileService) {
        this.repository = repository;
        this.fileService = fileService;
    }

    @GetMapping
    public List<Company> all() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "*")
    public Company one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(id));
    }

    @PatchMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        Company company = repository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(id));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Company.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, company, value);
        });
        return ResponseEntity.ok(repository.save(company));
    }

    @PostMapping
    public ResponseEntity<?> newCompany(@RequestBody Company company) {
        return ResponseEntity.ok(repository.save(company));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/logos/{fileName}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Resource> getLogo(@PathVariable String fileName) {
        try {
            Path absolutePath = Paths.get("").toAbsolutePath();
            Path fileStorageLocation = Paths.get(absolutePath+"/uploads").toAbsolutePath().normalize();
            Path filePath = fileStorageLocation.resolve(fileName).normalize();

            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] fileContent = Files.readAllBytes(filePath);

            ByteArrayResource resource = new ByteArrayResource(fileContent);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .headers(headers)
                    .body(resource);
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/logos/{id}")
    @CrossOrigin(origins = "*")
    public String uploadLogo(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Company company = repository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(id));
        String logoUrl = fileService.uploadCompanyLogo.apply(file);
        company.setLogoPath(logoUrl);
        repository.save(company);
        return logoUrl;
    }
}
