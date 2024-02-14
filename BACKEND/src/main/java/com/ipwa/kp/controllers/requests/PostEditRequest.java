package com.ipwa.kp.controllers.requests;

import java.time.LocalDate;

public class PostEditRequest {
    private String workName;
    private String workDescription;
    private Integer Salary;
    private String claims;
    private String additionalInfo;
    private LocalDate expiryDate;

    public PostEditRequest(String workName, String workDescription, Integer salary, String claims, String additionalInfo, LocalDate expiryDate) {
        this.workName = workName;
        this.workDescription = workDescription;
        Salary = salary;
        this.claims = claims;
        this.additionalInfo = additionalInfo;
        this.expiryDate = expiryDate;
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
        return Salary;
    }

    public void setSalary(Integer salary) {
        Salary = salary;
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

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
