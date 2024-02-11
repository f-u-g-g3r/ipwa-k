package com.ipwa.kp.controllers;

import com.ipwa.kp.controllers.exceptions.CompanyNotFoundException;
import com.ipwa.kp.models.Company;
import com.ipwa.kp.repositories.CompanyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyRepository repository;

    public CompanyController(CompanyRepository repository) {
        this.repository = repository;
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
}
