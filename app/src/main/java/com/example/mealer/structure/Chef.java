package com.example.mealer.structure;

import java.util.ArrayList;

public class Chef extends User {
    private String description, suspensionLength;
    private ArrayList<Complaint> listOfComplaints;
    private int numberOfComplaints;
    private Boolean isSuspended;

    public Chef() { }

    public Chef(String firstName, String lastName, String email, String address, String payment, String description) {
        super(firstName, lastName, email, address, "Chef");
        setPayment(payment);
        this.description = description;
        suspensionLength = "none";
        isSuspended = false;
        listOfComplaints = new ArrayList<>();
        this.numberOfComplaints = 0;
    }

    public String getSuspensionLength() { return suspensionLength; }

    public void setSuspensionLength(String suspensionLength) { this.suspensionLength = suspensionLength; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Boolean getIsSuspended() { return this.isSuspended; }

    public void setIsSuspended(Boolean isSuspended) { this.isSuspended = isSuspended; }

    public ArrayList<Complaint> getListOfComplaints() { return listOfComplaints; }

    public void setListOfComplaints(ArrayList<Complaint> listOfComplaints) { this.listOfComplaints = listOfComplaints; }

    public void addNewComplaint(Complaint complaint) { listOfComplaints.add(complaint); }

    public void removeComplaint(Complaint complaint) { listOfComplaints.remove(complaint); }

    public int getNumberOfComplaints() { return listOfComplaints.size(); }

}
