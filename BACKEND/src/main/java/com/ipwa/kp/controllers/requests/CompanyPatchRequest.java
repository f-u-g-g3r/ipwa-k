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
}
