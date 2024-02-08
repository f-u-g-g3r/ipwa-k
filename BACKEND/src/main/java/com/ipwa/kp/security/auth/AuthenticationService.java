package com.ipwa.kp.security.auth;

import com.ipwa.kp.models.Company;
import com.ipwa.kp.models.InternshipCoordinator;
import com.ipwa.kp.models.Student;
import com.ipwa.kp.models.Teacher;
import com.ipwa.kp.repositories.*;
import com.ipwa.kp.security.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsRepository userDetailsRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CompanyRepository companyRepository;
    private final InternshipCoordinatorRepository coordinatorRepository;
    private final JwtService jwtService;

    public AuthenticationService(AuthenticationManager authenticationManager, UserDetailsRepository userDetailsRepository, StudentRepository studentRepository, TeacherRepository teacherRepository, CompanyRepository companyRepository, InternshipCoordinatorRepository coordinatorRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsRepository = userDetailsRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.companyRepository = companyRepository;
        this.coordinatorRepository = coordinatorRepository;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String username = request.getUsername();
        UserDetails userDetails = userDetailsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
        HashMap extraClaims = new HashMap();
        String jwtToken;
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            switch (authority.getAuthority()) {
                case "STUDENT":
                    Student student = studentRepository.findByEmail(username)
                            .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
                    extraClaims.put("id", student.getId());
                    extraClaims.put("authority", student.getAuthorities());
                    jwtToken = jwtService.generateToken(extraClaims, student);
                    return new AuthenticationResponse(jwtToken, student.getId(), student.getAuthorities());

                case "TEACHER":
                    Teacher teacher = teacherRepository.findByEmail(username)
                            .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
                    extraClaims.put("id", teacher.getId());
                    extraClaims.put("authority", teacher.getAuthorities());
                    jwtToken = jwtService.generateToken(extraClaims, teacher);
                    return new AuthenticationResponse(jwtToken, teacher.getId(), teacher.getAuthorities());

                case "COMPANY":
                    Company company = companyRepository.findByEmail(username)
                            .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
                    extraClaims.put("id", company.getId());
                    extraClaims.put("authority", company.getAuthorities());
                    jwtToken = jwtService.generateToken(extraClaims, company);
                    return new AuthenticationResponse(jwtToken, company.getId(), company.getAuthorities());

                case "COORDINATOR":
                    InternshipCoordinator coordinator = coordinatorRepository.findByEmail(username)
                            .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
                    extraClaims.put("id", coordinator.getId());
                    extraClaims.put("authority", coordinator.getAuthorities());
                    jwtToken = jwtService.generateToken(extraClaims, coordinator);
                    return new AuthenticationResponse(jwtToken, coordinator.getId(), coordinator.getAuthorities());

                default:
                    break;
            }
        }
        return new AuthenticationResponse("Invalid credentials");
    }
}
