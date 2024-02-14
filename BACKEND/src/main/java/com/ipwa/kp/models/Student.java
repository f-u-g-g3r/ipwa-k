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


    @ManyToMany(mappedBy = "students")
    private List<Post> posts = new ArrayList<>();

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Status accountStatus = Status.INACTIVE;

    private int appliedFor;

    public Student() {

    }

    public Student(Long id, Resume resume, Teacher teacher, ClassGroup classGroup, List<Post> posts, String username, String firstName, String lastName, String email, String password, Status accountStatus, int appliedFor) {
        this.id = id;
        this.resume = resume;
        this.teacher = teacher;
        this.classGroup = classGroup;
        this.posts = posts;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.accountStatus = accountStatus;
        this.appliedFor = appliedFor;
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

    public List<Long> getPosts() {
        return posts.stream()
                .map(Post::getId)
                .collect(Collectors.toList());
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
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

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAppliedFor() {
        return appliedFor;
    }

    public void setAppliedFor(int appliedFor) {
        this.appliedFor = appliedFor;
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
                ", appliedFor=" + appliedFor +
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
