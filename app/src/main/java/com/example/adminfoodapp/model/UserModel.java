package com.example.adminfoodapp.model;


public class UserModel {
    private String name;
    private String nameOfRestaurant;
    private String email;
    private String password;
    private String address;
    private String phone;

    public UserModel() {
    }
    public UserModel(String name, String nameOfRestaurant, String email, String password) {
        this.name = name;
        this.nameOfRestaurant = nameOfRestaurant;
        this.email = email;
        this.password = password;
    }

    public UserModel(String name, String phone, String password, String email, String nameOfRestaurant, String address) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.nameOfRestaurant = nameOfRestaurant;
        this.address = address;
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

    public String getNameOfRestaurant() {
        return nameOfRestaurant;
    }

    public void setNameOfRestaurant(String nameOfRestaurant) {
        this.nameOfRestaurant = nameOfRestaurant;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}