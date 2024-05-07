package com.ipwa.kp.controllers;

import com.ipwa.kp.controllers.requests.CompanyPatchRequest;
import com.ipwa.kp.models.Company;
import com.ipwa.kp.services.CompanyService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Company> all(@RequestParam Optional<String> sortBy,
                             @RequestParam Optional<Integer> page,
                             @RequestParam Optional<String> direction) {
        return service.getAll(sortBy, page, direction);
    }

    @GetMapping("/{id}")
    public Company one(@PathVariable Long id) {
        return service.getOneById(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('COORDINATOR') or hasAuthority('COMPANY') and #id == authentication.principal.id")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @RequestBody CompanyPatchRequest request) {
        return service.updateCompany(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('COORDINATOR')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.deleteById(id);
    }

    @GetMapping("/posts/{studentId}")
    public List<Company> oneByStudentId(@PathVariable Long studentId) {
        return service.oneByStudentId(studentId);
    }

    @GetMapping("/logos/{fileName}")
    public ResponseEntity<Resource> getLogo(@PathVariable String fileName) {
        return service.getLogo(fileName);
    }

    @PutMapping("/logos/{id}")
    @PreAuthorize("hasAuthority('COMPANY') and #id == authentication.principal.id")
    public String uploadLogo(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return service.uploadLogo(id, file);
    }
}
