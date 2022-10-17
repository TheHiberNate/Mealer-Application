package com.example.mealer;

public class Chef extends User {
    public Chef(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        setRole("Chef");
    }
}
