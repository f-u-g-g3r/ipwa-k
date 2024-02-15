package com.ipwa.kp.models;

import jakarta.persistence.*;


@Entity
@Table(name = "resume")
public class Resume {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    private String cv;
    private String motivationLetter;


    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Resume() {
    }

    public Resume(Long id, String cv, String motivationLetter, Student student) {
        this.id = id;
        this.cv = cv;
        this.motivationLetter = motivationLetter;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getMotivationLetter() {
        return motivationLetter;
    }

    public void setMotivationLetter(String motivationLetter) {
        this.motivationLetter = motivationLetter;
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
                ", student=" + student.toString() +
                '}';
    }
}
