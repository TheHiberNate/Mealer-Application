package com.example.mealer;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String role;
    //private String address;


    public User(String firstName, String lastName, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        //this.address = address;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() { return role; }
}
