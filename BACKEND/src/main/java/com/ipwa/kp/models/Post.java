package com.ipwa.kp.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "posts")
public class Post {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "post_student",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students = new ArrayList<>();

    private String workName;
    private String workDescription;
    private int salary;
    private String claims;
    private String additionalInfo;
    private Date expiryDate;
    private Date datePosted;

    public Post() {

    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getClaims() {
        return claims;
    }

    public void setClaims(String claims) {
        this.claims = claims;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return salary == post.salary && Objects.equals(id, post.id) && Objects.equals(company, post.company) && Objects.equals(students, post.students) && Objects.equals(workName, post.workName) && Objects.equals(workDescription, post.workDescription) && Objects.equals(claims, post.claims) && Objects.equals(additionalInfo, post.additionalInfo) && Objects.equals(expiryDate, post.expiryDate) && Objects.equals(datePosted, post.datePosted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, company, students, workName, workDescription, salary, claims, additionalInfo, expiryDate, datePosted);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", company=" + company +
                ", students=" + students +
                ", workName='" + workName + '\'' +
                ", workDescription='" + workDescription + '\'' +
                ", salary=" + salary +
                ", claims='" + claims + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", expiryDate=" + expiryDate +
                ", datePosted=" + datePosted +
                '}';
    }
}
