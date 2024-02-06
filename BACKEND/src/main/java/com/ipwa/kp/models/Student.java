package com.ipwa.kp.models;

import com.ipwa.kp.models.enums.Status;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "students")
public class Student {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToMany(mappedBy = "students")
    private List<Post> posts = new ArrayList<>();

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Status accountStatus;

    public Student() {}

    public Student(Long id, Resume resume, Teacher teacher, List<Post> posts, String firstName, String lastName, String email, String password, Status accountStatus) {
        this.id = id;
        this.resume = resume;
        this.teacher = teacher;
        this.posts = posts;
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

    public List<Post> getPosts() {
        return posts;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(resume, student.resume) && Objects.equals(teacher, student.teacher) && Objects.equals(posts, student.posts) && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName) && Objects.equals(email, student.email) && Objects.equals(password, student.password) && accountStatus == student.accountStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, resume, teacher, posts, firstName, lastName, email, password, accountStatus);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", resume=" + resume +
                ", teacher=" + teacher +
                ", posts=" + posts +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", accountStatus=" + accountStatus +
                '}';
    }
}
