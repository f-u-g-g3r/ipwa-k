package com.ipwa.kp.security.auth;

import com.ipwa.kp.models.Student;
import com.ipwa.kp.repositories.StudentRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final StudentRepository studentRepository;

    public AuthenticationController(AuthenticationService authenticationService, StudentRepository studentRepository) {
        this.authenticationService = authenticationService;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        if (studentRepository.findByEmail(request.getUsername()).isPresent()) {
            return authenticationService.authenticate(request);
        }
        return new AuthenticationResponse("Error");
    }

    @PostMapping("/test")
    public AuthenticationResponse test(@RequestBody Student student) {
        return authenticationService.registerUser(student);
    }


}
