package com.example.mealer.structure;

public class Chef extends User {
    public Chef(String firstName, String lastName, String email, String address, int payment) {
        super(firstName, lastName, email, address, "Chef");
        setPayment(payment);
    }
}
