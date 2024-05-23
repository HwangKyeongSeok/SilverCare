package com.example.sivercare;

public class User {
    private String id;
    private String password;
    private String phone;
    private String email;
    private String name;

    public User(String id, String password, String phone, String email, String name) {
        this.id = id;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
