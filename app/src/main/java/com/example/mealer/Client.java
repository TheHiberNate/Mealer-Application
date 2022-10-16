package com.example.mealer;

public class Client extends User{
    public Client(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        setRole("Client");
    }
}
