package com.ipwa.kp.security.config;

import com.ipwa.kp.models.Company;
import com.ipwa.kp.models.InternshipCoordinator;
import com.ipwa.kp.models.Student;
import com.ipwa.kp.models.Teacher;
import com.ipwa.kp.repositories.CompanyRepository;
import com.ipwa.kp.repositories.InternshipCoordinatorRepository;
import com.ipwa.kp.repositories.StudentRepository;
import com.ipwa.kp.repositories.TeacherRepository;
import com.ipwa.kp.security.customUserDetails.CustomUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final CustomUserDetailsImpl customUserDetails;

    public ApplicationConfig(CustomUserDetailsImpl customUserDetails) {
        this.customUserDetails = customUserDetails;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetails);
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
