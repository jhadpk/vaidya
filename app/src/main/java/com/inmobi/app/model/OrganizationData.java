package com.inmobi.app.model;

/**
 * Created by krishna.tiwari on 15/08/15.
 */
public class OrganizationData {

    private String name;
    private String phone;
    private String city;
    private String address;
    private String email;
    private String password;


    public OrganizationData(String name, String phone, String city, String address, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
