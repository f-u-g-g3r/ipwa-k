package com.ipwa.kp.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "posts")
public class Post {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    //add relationship
    private Long companyId;
    private String workName;
    private String workDescription;
    private int salary;
    private String claims;
    private String additionalInfo;
    private Date expiryDate;

    public Post() {

    }

    public Post(Long id, Long companyId, String workName, String workDescription, int salary, String claims, String additionalInfo, Date expiryDate) {
        this.id = id;
        this.companyId = companyId;
        this.workName = workName;
        this.workDescription = workDescription;
        this.salary = salary;
        this.claims = claims;
        this.additionalInfo = additionalInfo;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return salary == post.salary && Objects.equals(id, post.id) && Objects.equals(companyId, post.companyId) && Objects.equals(workName, post.workName) && Objects.equals(workDescription, post.workDescription) && Objects.equals(claims, post.claims) && Objects.equals(additionalInfo, post.additionalInfo) && Objects.equals(expiryDate, post.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyId, workName, workDescription, salary, claims, additionalInfo, expiryDate);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", workName='" + workName + '\'' +
                ", workDescription='" + workDescription + '\'' +
                ", salary=" + salary +
                ", claims='" + claims + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
