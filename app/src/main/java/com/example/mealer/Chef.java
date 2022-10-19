package com.example.mealer;

public class Chef extends User {
    public Chef(String firstName, String lastName, String email, String address, String payment) {
        super(firstName, lastName, email, address, "Chef");
        setPayment(payment);
    }
}
