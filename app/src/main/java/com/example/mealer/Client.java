package com.example.mealer;

public class Client extends User{
    public Client(String firstName, String lastName, String email, String address) {
        super(firstName, lastName, email, address);
        setRole("Client");
    }
}
