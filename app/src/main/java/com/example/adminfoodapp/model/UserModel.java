package com.example.adminfoodapp.model;

public class UserModel {
    private String name;
    private String nameOfRestaurant;
    private String email;
    private String password;
    private String address;
    private String phone;

    public UserModel() {
        // Constructor mặc định
    }

    public UserModel(String name, String nameOfRestaurant, String email, String password, String address, String phone) {
        this.name = name;
        this.nameOfRestaurant = nameOfRestaurant;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    // Getter và Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameOfRestaurant() {
        return nameOfRestaurant;
    }

    public void setNameOfRestaurant(String nameOfRestaurant) {
        this.nameOfRestaurant = nameOfRestaurant;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
