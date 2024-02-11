package com.ipwa.kp.security.auth;

import com.ipwa.kp.models.Company;
import com.ipwa.kp.models.Student;
import com.ipwa.kp.repositories.StudentRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final StudentRepository studentRepository;
    private final UserDetailsService userDetailsService;

    public AuthenticationController(AuthenticationService authenticationService, StudentRepository studentRepository, UserDetailsService userDetailsService) {
        this.authenticationService = authenticationService;
        this.studentRepository = studentRepository;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/authenticate")
    @CrossOrigin(origins = "*")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
            return authenticationService.authenticate(request);
        //return new AuthenticationResponse("Error");
    }

    @PostMapping("/students")
    public AuthenticationResponse newStudent(@RequestBody Student student) {
        return authenticationService.registerStudent(student);
    }

    @PostMapping("/companies")
    @PreAuthorize("hasAuthority('COORDINATOR')")
    @CrossOrigin(origins = "*")
    public AuthenticationResponse newCompany(@RequestBody Company company) {
        return authenticationService.registerCompany(company);
    }


}
