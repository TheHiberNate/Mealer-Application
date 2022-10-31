package com.example.mealer.structure;

public class Chef extends User {
    private String description;

    public Chef(String firstName, String lastName, String email, String address, String payment, String description) {
        super(firstName, lastName, email, address, "Chef");
        setPayment(payment);
        this.description = description;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
