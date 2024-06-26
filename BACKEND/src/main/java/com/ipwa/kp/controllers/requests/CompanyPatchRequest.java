package com.ipwa.kp.controllers.requests;

public class CompanyPatchRequest {
    private String name;
    private String username;
    private String registryCode;
    private String email;
    private String phone;
    private String contacts;
    private String address;

    public CompanyPatchRequest() {
    }

    public CompanyPatchRequest(String name, String username, String registryCode, String email, String phone, String contacts, String address) {
        this.name = name;
        this.username = username;
        this.registryCode = registryCode;
        this.email = email;
        this.phone = phone;
        this.contacts = contacts;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getRegistryCode() {
        return registryCode;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getContacts() {
        return contacts;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRegistryCode(String registryCode) {
        this.registryCode = registryCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
