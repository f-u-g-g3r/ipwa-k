package com.ipwa.kp.security.config;

import com.ipwa.kp.models.Company;
import com.ipwa.kp.models.InternshipCoordinator;
import com.ipwa.kp.models.Student;
import com.ipwa.kp.models.Teacher;
import com.ipwa.kp.repositories.CompanyRepository;
import com.ipwa.kp.repositories.InternshipCoordinatorRepository;
import com.ipwa.kp.repositories.StudentRepository;
import com.ipwa.kp.repositories.TeacherRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CompanyRepository companyRepository;
    private final InternshipCoordinatorRepository coordinatorRepository;

    public ApplicationConfig(StudentRepository studentRepository, TeacherRepository teacherRepository, CompanyRepository companyRepository, InternshipCoordinatorRepository coordinatorRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.companyRepository = companyRepository;
        this.coordinatorRepository = coordinatorRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Student student = studentRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));





            return new User(
                    student.getUsername(),
                    student.getPassword(),
                    student.getAuthorities()
            );
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
