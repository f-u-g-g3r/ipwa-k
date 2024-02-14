package com.ipwa.kp.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "resume")
public class Resume {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String path;
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Resume() {
    }

    public Resume(Long id, String path, Student student) {
        this.id = id;
        this.path = path;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", student=" + student.toString() +
                '}';
    }
}
