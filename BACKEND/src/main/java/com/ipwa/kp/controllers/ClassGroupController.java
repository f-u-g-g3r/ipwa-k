package com.ipwa.kp.controllers;

import com.ipwa.kp.models.ClassGroup;
import com.ipwa.kp.services.ClassGroupService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/class-groups")
@RestController
public class ClassGroupController {
    private final ClassGroupService service;

    public ClassGroupController(ClassGroupService service) {
        this.service = service;
    }

    @GetMapping
    public List<ClassGroup> all() {
        return service.getAll();
    }

    @GetMapping("/pages")
    public Page<ClassGroup> allByPage(@RequestParam Optional<String> sortBy,
                                      @RequestParam Optional<Integer> page,
                                      @RequestParam Optional<String> direction) {
       return service.getAllByPage(sortBy, page, direction);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('COORDINATOR')")
    public ResponseEntity<?> createClassGroup(@RequestBody ClassGroup classGroup) {
        return service.createClassGroup(classGroup);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getClassGroupByName(@PathVariable String name) {
        return service.getClassGroupByName(name);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('COORDINATOR')")
    public void deleteGroup(@PathVariable Long id) {
        service.deleteGroupById(id);
    }

}
