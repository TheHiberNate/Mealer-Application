package com.example.mealer;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    //private String address;


    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
//        this.role = role;
        //this.address = address;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
