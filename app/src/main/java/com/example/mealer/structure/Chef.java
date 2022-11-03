package com.example.mealer.structure;

import java.util.ArrayList;

public class Chef extends User {
    private String description;
    private ArrayList<Complaint> listOfComplaints;
    private int numberOfComplaints;
    private Boolean isSuspended = false;


    public Chef(String firstName, String lastName, String email, String address, int payment, String description) {
        super(firstName, lastName, email, address, "Chef");
        setPayment(payment);
        this.description = description;
        listOfComplaints = new ArrayList<>();
        this.numberOfComplaints = 0;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public ArrayList<Complaint> getListOfComplaints() { return listOfComplaints; }

    public void setListOfComplaints(ArrayList<Complaint> listOfComplaints) { this.listOfComplaints = listOfComplaints; }

    public void addNewComplaint(Complaint complaint) { listOfComplaints.add(complaint); }

    public void removeComplaint(Complaint complaint) { listOfComplaints.remove(complaint); }

    public int getNumberOfComplaints() { return listOfComplaints.size(); }

}
