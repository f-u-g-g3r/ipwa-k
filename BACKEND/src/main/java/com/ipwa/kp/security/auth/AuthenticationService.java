package com.ipwa.kp.security.auth;

import com.ipwa.kp.models.Company;
import com.ipwa.kp.models.InternshipCoordinator;
import com.ipwa.kp.models.Student;
import com.ipwa.kp.models.Teacher;
import com.ipwa.kp.repositories.*;
import com.ipwa.kp.security.config.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CompanyRepository companyRepository;
    private final InternshipCoordinatorRepository coordinatorRepository;
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, StudentRepository studentRepository, TeacherRepository teacherRepository, CompanyRepository companyRepository, InternshipCoordinatorRepository coordinatorRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.companyRepository = companyRepository;
        this.coordinatorRepository = coordinatorRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String username = request.getUsername();
        Student student = studentRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
        HashMap extraClaims = new HashMap();
        String jwtToken;
        extraClaims.put("id", student.getId());
        extraClaims.put("authority", student.getAuthorities());
        jwtToken = jwtService.generateToken(extraClaims, student);
        return new AuthenticationResponse(jwtToken, student.getId(), student.getAuthorities());

//        for (GrantedAuthority authority : student.getAuthorities()) {
//            switch (authority.getAuthority()) {
//                case "STUDENT":
//                    Student student = studentRepository.findByEmail(username)
//                            .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
//                    extraClaims.put("id", student.getId());
//                    extraClaims.put("authority", student.getAuthorities());
//                    jwtToken = jwtService.generateToken(extraClaims, student);
//                    return new AuthenticationResponse(jwtToken, student.getId(), student.getAuthorities());
//
//                case "TEACHER":
//                    Teacher teacher = teacherRepository.findByEmail(username)
//                            .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
//                    extraClaims.put("id", teacher.getId());
//                    extraClaims.put("authority", teacher.getAuthorities());
//                    jwtToken = jwtService.generateToken(extraClaims, teacher);
//                    return new AuthenticationResponse(jwtToken, teacher.getId(), teacher.getAuthorities());
//
//                case "COMPANY":
//                    Company company = companyRepository.findByEmail(username)
//                            .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
//                    extraClaims.put("id", company.getId());
//                    extraClaims.put("authority", company.getAuthorities());
//                    jwtToken = jwtService.generateToken(extraClaims, company);
//                    return new AuthenticationResponse(jwtToken, company.getId(), company.getAuthorities());
//
//                case "COORDINATOR":
//                    InternshipCoordinator coordinator = coordinatorRepository.findByEmail(username)
//                            .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
//                    extraClaims.put("id", coordinator.getId());
//                    extraClaims.put("authority", coordinator.getAuthorities());
//                    jwtToken = jwtService.generateToken(extraClaims, coordinator);
//                    return new AuthenticationResponse(jwtToken, coordinator.getId(), coordinator.getAuthorities());
//
//                default:
//                    break;
//            }
//        }
    }

    public AuthenticationResponse registerUser(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentRepository.save(student);

        HashMap extraClaims = new HashMap();
        extraClaims.put("id", student.getId());
        extraClaims.put("authority", student.getAuthorities());

        String jwtToken =jwtService.generateToken(extraClaims, student);

        return new AuthenticationResponse(jwtToken, student.getId(), student.getAuthorities());
    }
}
