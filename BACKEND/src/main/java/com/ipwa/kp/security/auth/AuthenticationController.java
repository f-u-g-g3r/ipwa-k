package com.ipwa.kp.security.auth;

import com.ipwa.kp.models.Company;
import com.ipwa.kp.models.Student;
import com.ipwa.kp.models.Teacher;
import com.ipwa.kp.repositories.StudentRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
            return authenticationService.authenticate(request);
    }

    @PostMapping("/students")
    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'TEACHER')")
    public AuthenticationResponse newStudent(@RequestBody Student student, Authentication authentication) {
        return authenticationService.registerStudent(student, authentication);
    }

    @PostMapping("/companies")
    @PreAuthorize("hasAuthority('COORDINATOR')")
    public AuthenticationResponse newCompany(@RequestBody Company company) {
        return authenticationService.registerCompany(company);
    }
    @PostMapping("/teachers")
    @PreAuthorize("hasAuthority('COORDINATOR')")
    public AuthenticationResponse newTeacher(@RequestBody Teacher teacher) {
        return authenticationService.registerTeacher(teacher);
    }


}
