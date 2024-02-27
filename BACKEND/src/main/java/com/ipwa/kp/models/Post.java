package com.ipwa.kp.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "posts")
public class Post {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "post")
    private List<PostStudent> postsStudents = new ArrayList<>();


    private String workName;
    private String workDescription;
    private Integer salary;
    private String claims;
    private String additionalInfo;
    private String pathToPdf;
    private LocalDate expiryDate;
    private LocalDate datePosted = LocalDate.now();

    public Post() {

    }

    public Post(Long id, Company company, List<PostStudent> postsStudents, String workName, String workDescription, Integer salary, String claims, String additionalInfo, String pathToPdf, LocalDate expiryDate, LocalDate datePosted) {
        this.id = id;
        this.company = company;
        this.postsStudents = postsStudents;
        this.workName = workName;
        this.workDescription = workDescription;
        this.salary = salary;
        this.claims = claims;
        this.additionalInfo = additionalInfo;
        this.pathToPdf = pathToPdf;
        this.expiryDate = expiryDate;
        this.datePosted = datePosted;
    }

    public LocalDate getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDate datePosted) {
        this.datePosted = datePosted;
    }

    public String getPathToPdf() {
        return pathToPdf;
    }

    public void setPathToPdf(String pathToPdf) {
        this.pathToPdf = pathToPdf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompany() {
        return company.getId();
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

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
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

    public String getExpiryDate() {
        if (expiryDate != null) {
            return expiryDate.format(DATE_FORMATTER);
        } else {
            return null;
        }
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = LocalDate.parse(expiryDate, DATE_FORMATTER);
    }


    public List<PostStudent> getPostStudents() {
        return postsStudents;
    }

    public void setPostsStudents(List<PostStudent> postsStudents) {
        this.postsStudents = postsStudents;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", workName='" + workName + '\'' +
                ", workDescription='" + workDescription + '\'' +
                ", salary=" + salary +
                ", claims='" + claims + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", pathToPdf='" + pathToPdf + '\'' +
                ", expiryDate=" + expiryDate +
                ", datePosted=" + datePosted +
                '}';
    }
}
