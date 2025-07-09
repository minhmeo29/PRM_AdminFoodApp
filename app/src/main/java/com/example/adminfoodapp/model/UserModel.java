package com.example.adminfoodapp.model;

public class UserModel {
    private String name;
    private String nameOfRestaurant;
    private String email;
    private String password;
    private String address;
    private String phone;

    // Required empty constructor for Firebase
    public UserModel() {
    }

    // Constructor for creating a user with minimal details
    public UserModel(String name,String nameOfRestaurant, String email, String password) {
        this.name = name;
        this.nameOfRestaurant = nameOfRestaurant;
        this.email = email;
        this.password = password;
    }

    public UserModel(String email, String password)
    {
        this.email = email;
        this.password = password;
    }


    // Full constructor
    public UserModel(String name, String nameOfRestaurant, String email, String password, String address, String phone) {
        this.name = name;
        this.nameOfRestaurant = nameOfRestaurant;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    // Getters and setters (you can use Lombok or generate automatically)
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