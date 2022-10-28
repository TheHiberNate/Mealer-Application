package com.example.mealer.structure;

import java.util.ArrayList;

public class Complaint {
    private String title;
    private String chefID;
    private String clientID;
    private String description;
    private int numComplaints = 0;

    public Complaint() {}

    public Complaint(String title,String chefID, String description) {
        this.chefID = chefID;
        this.description = description;
    }

    public void addComplaint() { numComplaints++; }

    public void removeComplaint() { numComplaints--; }

    public int getNumComplaints() { return numComplaints; }
}
