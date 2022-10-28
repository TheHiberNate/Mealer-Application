package com.example.mealer.structure;

public class Chef extends User {
    private String description;
    private int numberComplaints;


    public Chef(String firstName, String lastName, String email, String address, int payment, String description) {
        super(firstName, lastName, email, address, "Chef");
        setPayment(payment);
        this.description = description;
        this.numberComplaints = new Complaint().getNumComplaints();
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
