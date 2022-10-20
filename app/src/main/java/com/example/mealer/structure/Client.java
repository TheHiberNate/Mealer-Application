package com.example.mealer.structure;

public class Client extends User{
    public Client(String firstName, String lastName, String email, String address, String payment) {
        super(firstName, lastName, email, address, "Client");
        setPayment(payment);
    }
}
