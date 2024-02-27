package com.ipwa.kp.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "post_student")
public class PostStudent {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    private String status = "Submitted";

    public PostStudent() {
    }

    public PostStudent(Post post, Student student) {
        this.post = post;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPost() {
        return post.getId();
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Long getStudent() {
        return student.getId();
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getCreatedAt() {
        if (createdAt != null) {
            return createdAt.format(DATE_FORMATTER);
        } else {
            return null;
        }
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostStudent that = (PostStudent) o;
        return Objects.equals(id, that.id) && Objects.equals(post, that.post) && Objects.equals(student, that.student) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, post, student, createdAt);
    }

    @Override
    public String toString() {
        return "PostStudent{" +
                "id=" + id +
                ", post=" + post +
                ", student=" + student +
                ", createdAt=" + createdAt +
                '}';
    }
}
