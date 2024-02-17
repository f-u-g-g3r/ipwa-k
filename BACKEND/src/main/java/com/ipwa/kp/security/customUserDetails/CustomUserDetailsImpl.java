package com.ipwa.kp.security.customUserDetails;

import com.ipwa.kp.models.Company;
import com.ipwa.kp.models.InternshipCoordinator;
import com.ipwa.kp.models.Student;
import com.ipwa.kp.models.Teacher;
import com.ipwa.kp.repositories.CompanyRepository;
import com.ipwa.kp.repositories.InternshipCoordinatorRepository;
import com.ipwa.kp.repositories.StudentRepository;
import com.ipwa.kp.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsImpl implements CustomUserDetailsService{
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CompanyRepository companyRepository;
    private final InternshipCoordinatorRepository coordinatorRepository;

    public CustomUserDetailsImpl(StudentRepository studentRepository, TeacherRepository teacherRepository, CompanyRepository companyRepository, InternshipCoordinatorRepository coordinatorRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.companyRepository = companyRepository;
        this.coordinatorRepository = coordinatorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(username)
                .orElseGet(() -> studentRepository.findByUsername(username)
                        .orElse(null));

        if (student == null) {
            Teacher teacher = teacherRepository.findByEmail(username)
                    .orElseGet(() -> teacherRepository.findByUsername(username)
                            .orElse(null));

            if (teacher != null) {
                return new CustomUserDetails(
                        teacher.getId(),
                        teacher.getUsername(),
                        teacher.getPassword(),
                        teacher.getAuthorities()
                );
            } else {
                Company company = companyRepository.findByEmail(username)
                        .orElseGet(() -> companyRepository.findByUsername(username)
                                .orElse(null));

                if (company != null) {
                    return new CustomUserDetails(
                            company.getId(),
                            company.getUsername(),
                            company.getPassword(),
                            company.getAuthorities()
                    );
                } else {
                    InternshipCoordinator coordinator = coordinatorRepository.findByEmail(username)
                            .orElse(null);

                    if (coordinator != null) {
                        return new CustomUserDetails(
                                coordinator.getId(),
                                coordinator.getUsername(),
                                coordinator.getPassword(),
                                coordinator.getAuthorities()
                        );
                    }
                }
            }
        } else {
            return new CustomUserDetails(
                    student.getId(),
                    student.getUsername(),
                    student.getPassword(),
                    student.getAuthorities()
            );
        }

        throw new UsernameNotFoundException(username);
    }
}
