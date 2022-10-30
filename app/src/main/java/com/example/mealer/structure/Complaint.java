package com.example.mealer.structure;

import java.util.ArrayList;

public class Complaint {
    private String title;
    private String description;
    private String clientID;
    private int numComplaints = 0;

    public Complaint() {}

    public Complaint(String title, String description, String clientID) {
        this.title = title;
        this.description = description;
        this.clientID = clientID;
    }

    public String getDescription() {
        return description;
    }

    public String getClientID() {
        return clientID;
    }

    public String getTitle() {
        return title;
    }

    public void addComplaint() { numComplaints++; }

    public void removeComplaint() { numComplaints--; }

    public int getNumComplaints() { return numComplaints; }
}
