package com.example.mealer.structure;

import java.util.ArrayList;

public class Complaint {
    private String title;
    private String description;
    private String clientID;
    private String chefID;

    public Complaint() {}

    public Complaint(String title, String description, String clientID, String chefID) {
        this.title = title;
        this.description = description;
        this.clientID = clientID;
        this.chefID = chefID;
    }

    public String getDescription() {
        return description;
    }

    public String getClientID() { return clientID; }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) { this.description = description; }

    public String getChefID() { return chefID; }

    public void setClientID(String clientID) { this.clientID = clientID; }

    public void setTitle(String title) { this.title = title; }
}
