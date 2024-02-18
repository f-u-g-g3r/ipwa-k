package com.ipwa.kp.models;

import com.ipwa.kp.models.enums.Status;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "students")
public class Student implements UserDetails {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_group_id")
    private ClassGroup classGroup;


    @OneToMany(mappedBy = "student")
    private List<PostStudent> postsStudents = new ArrayList<>();


    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Status accountStatus = Status.INACTIVE;

    public Student() {

    }

    public Student(Long id, Resume resume, Teacher teacher, ClassGroup classGroup, List<PostStudent> postsStudents, String username, String firstName, String lastName, String email, String password, Status accountStatus) {
        this.id = id;
        this.resume = resume;
        this.teacher = teacher;
        this.classGroup = classGroup;
        this.postsStudents = postsStudents;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.accountStatus = accountStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<PostStudent> getPostStudents() {
        return postsStudents;
    }

    public void setPostsStudents(List<PostStudent> postsStudents) {
        this.postsStudents = postsStudents;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Status accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Long getResume() {
        if (resume != null) {
            return resume.getId();
        }
        return null;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClassGroup() {
        if (classGroup != null) {
            return classGroup.getName();
        } else {
            return null;
        }
    }

    public void setClassGroup(ClassGroup classGroup) {
        this.classGroup = classGroup;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", resume=" + resume +
                ", teacher=" + teacher +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", classGroup='" + classGroup + '\'' +
                ", accountStatus=" + accountStatus +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("STUDENT"));
    }

    @Override
    public String getUsername() {
        if (username != null) {
            return username;
        } else {
            return email;
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
