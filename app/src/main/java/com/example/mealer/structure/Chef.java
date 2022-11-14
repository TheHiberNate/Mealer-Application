package com.example.mealer.structure;

import java.util.ArrayList;

public class Chef extends User {
    private String description, suspensionLength;
//    private ArrayList<Complaint> listOfComplaints;
//    private int numberOfComplaints;
    private Boolean suspended;
    private Menu menu;

    public Chef() { }

    public Chef(String firstName, String lastName, String email, String address, String payment, String description, Menu menu) {
        super(firstName, lastName, email, address, "Chef");
        setPayment(payment);
        this.description = description;
        this.suspensionLength = "none";
        this.suspended = false;
        this.menu = menu;

//        listOfComplaints = new ArrayList<>();
//        this.numberOfComplaints = 0;
    }

    public String getSuspensionLength() { return suspensionLength; }

    public void setSuspensionLength(String suspensionLength) { this.suspensionLength = suspensionLength; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Boolean getSuspended() { return this.suspended; }

    public void setIsSuspended(Boolean suspended) { this.suspended = suspended; }

    public Menu getMenu() { return menu; }

    public void setMenu(Menu menu) { this.menu = menu; }

    //    public ArrayList<Complaint> getListOfComplaints() { return listOfComplaints; }

//    public void setListOfComplaints(ArrayList<Complaint> listOfComplaints) { this.listOfComplaints = listOfComplaints; }

//    public void addNewComplaint(Complaint complaint) { listOfComplaints.add(complaint); }
//
//    public void removeComplaint(Complaint complaint) { listOfComplaints.remove(complaint); }
//
//    public int getNumberOfComplaints() { return listOfComplaints.size(); }

}
