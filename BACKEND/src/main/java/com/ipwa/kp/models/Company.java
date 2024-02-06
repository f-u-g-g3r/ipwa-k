package com.ipwa.kp.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "companies")
public class Company {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String companyName;
    private String registryCode;
    private String email;
    private String phone;
    private String contacts;
    private String address;

    public Company() {
    }

    public Company(Long id, String companyName, String registryCode, String email, String phone, String contacts, String address) {
        this.id = id;
        this.companyName = companyName;
        this.registryCode = registryCode;
        this.email = email;
        this.phone = phone;
        this.contacts = contacts;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRegistryCode() {
        return registryCode;
    }

    public void setRegistryCode(String registryCode) {
        this.registryCode = registryCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id) && Objects.equals(companyName, company.companyName) && Objects.equals(registryCode, company.registryCode) && Objects.equals(email, company.email) && Objects.equals(phone, company.phone) && Objects.equals(contacts, company.contacts) && Objects.equals(address, company.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyName, registryCode, email, phone, contacts, address);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", registryCode='" + registryCode + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", contacts='" + contacts + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
