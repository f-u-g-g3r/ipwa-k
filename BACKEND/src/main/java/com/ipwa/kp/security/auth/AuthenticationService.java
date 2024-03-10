package com.ipwa.kp.security.auth;

import com.ipwa.kp.models.*;
import com.ipwa.kp.repositories.*;
import com.ipwa.kp.security.config.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CompanyRepository companyRepository;
    private final InternshipCoordinatorRepository coordinatorRepository;
    private final UserDetailsService userDetailsService;
    private final ResumeRepository resumeRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, ResumeRepository resumeRepository, StudentRepository studentRepository, TeacherRepository teacherRepository, CompanyRepository companyRepository, InternshipCoordinatorRepository coordinatorRepository, UserDetailsService userDetailsService, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.companyRepository = companyRepository;
        this.coordinatorRepository = coordinatorRepository;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.resumeRepository = resumeRepository;
    }




    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String username = request.getUsername();
        UserDetails user = userDetailsService.loadUserByUsername(username);

        Map<String, Object> extraClaims = new LinkedHashMap<>();
        String jwtToken;

        for (GrantedAuthority authority : user.getAuthorities()) {
            switch (authority.getAuthority()) {
                case "STUDENT":
                    Student student = studentRepository.findByEmail(username)
                            .orElseGet(() -> studentRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username)));
                    extraClaims.put("id", student.getId());
                    extraClaims.put("authority", student.getAuthorities());
                    jwtToken = jwtService.generateToken(extraClaims, student);
                    return new AuthenticationResponse(jwtToken, student.getId(), student.getAuthorities());

                case "TEACHER":
                    Teacher teacher = teacherRepository.findByEmail(username)
                            .orElseGet(() -> teacherRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username)));
                    extraClaims.put("id", teacher.getId());
                    extraClaims.put("authority", teacher.getAuthorities());
                    jwtToken = jwtService.generateToken(extraClaims, teacher);
                    return new AuthenticationResponse(jwtToken, teacher.getId(), teacher.getAuthorities());

                case "COMPANY":
                    Company company = companyRepository.findByEmail(username)
                            .orElseGet(() -> companyRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username)));
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
        return new AuthenticationResponse("error");
    }

    public AuthenticationResponse registerStudent(Student student) {
        if (isUsernameNotTaken(student.getUsername()) && isEmailNotTaken(student.getUsername())) {
            student.setPassword(passwordEncoder.encode(student.getPassword()));
            studentRepository.save(student);
            Resume resume = new Resume();
            resume.setStudent(student);
            student.setResume(resume);
            resumeRepository.save(resume);

            Map<String, Object> extraClaims = new LinkedHashMap<>();
            extraClaims.put("id", student.getId());
            extraClaims.put("authority", student.getAuthorities());

            String jwtToken = jwtService.generateToken(extraClaims, student);

            return new AuthenticationResponse(jwtToken, student.getId(), student.getAuthorities());
        } else {
            return new AuthenticationResponse("Username " + student.getUsername() + " is already taken.");
        }
    }

    public AuthenticationResponse registerCompany(Company company) {
        if (isUsernameNotTaken(company.getUsername()) && isEmailNotTaken(company.getUsername())) {
            company.setPassword(passwordEncoder.encode(company.getPassword()));
            companyRepository.save(company);

            Map<String, Object> extraClaims = new LinkedHashMap<>();
            extraClaims.put("id", company.getId());
            extraClaims.put("authority", company.getAuthorities());

            String jwtToken = jwtService.generateToken(extraClaims, company);

            return new AuthenticationResponse(jwtToken, company.getId(), company.getAuthorities());
        } else {
            return new AuthenticationResponse("Username " + company.getUsername() + " is already taken.");
        }
    }

    public AuthenticationResponse registerTeacher(Teacher teacher) {
        if (isUsernameNotTaken(teacher.getUsername()) && isEmailNotTaken(teacher.getUsername())) {
            teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
            teacherRepository.save(teacher);

            Map<String, Object> extraClaims = new LinkedHashMap<>();
            extraClaims.put("id", teacher.getId());
            extraClaims.put("authority", teacher.getAuthorities());

            String jwtToken = jwtService.generateToken(extraClaims, teacher);

            return new AuthenticationResponse(jwtToken, teacher.getId(), teacher.getAuthorities());
        } else {
            return new AuthenticationResponse("Username " + teacher.getUsername() + " is already taken.");
        }
    }

    public Boolean isEmailNotTaken(String email) {
        Boolean isTaken = false;
        isTaken = studentRepository.existsByEmail(email);
        if (!isTaken) {
            isTaken = teacherRepository.existsByEmail(email);
            if (!isTaken) {
                isTaken = companyRepository.existsByEmail(email);
                if (!isTaken) {
                    isTaken = coordinatorRepository.existsByEmail(email);
                }
            }
        }
        return !isTaken;
    }

    public Boolean isUsernameNotTaken(String username) {
        Boolean isTaken;
        isTaken = studentRepository.existsByUsername(username);
        if (!isTaken) {
            isTaken = teacherRepository.existsByUsername(username);
            if (!isTaken) {
                isTaken = companyRepository.existsByUsername(username);
            }
        }
        System.out.println(!isTaken);
        return !isTaken;
    }
}
