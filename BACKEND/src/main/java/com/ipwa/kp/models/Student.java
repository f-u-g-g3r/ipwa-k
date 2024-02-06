package com.ipwa.kp.models;

import com.ipwa.kp.models.enums.Status;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "students")
public class Student {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    //add relationship
    private Long teacherId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Status accountStatus;

    public Student() {}

    public Student(Long id, Long teacherId, String firstName, String lastName, String email, String password, Status accountStatus) {
        this.id = id;
        this.teacherId = teacherId;
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

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(teacherId, student.teacherId) && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName) && Objects.equals(email, student.email) && Objects.equals(password, student.password) && accountStatus == student.accountStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teacherId, firstName, lastName, email, password, accountStatus);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", teacherId=" + teacherId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", accountStatus=" + accountStatus +
                '}';
    }
}
