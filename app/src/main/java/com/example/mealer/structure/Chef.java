package com.example.mealer.structure;

import java.util.ArrayList;
import java.util.List;

public class Chef extends User {
    private String description, suspensionLength;
    private Boolean suspended;
    private Menu menu;
    private List<Order> orders;
    private String soldMeals;
    private String rating;

    public Chef() { }

    public Chef(String firstName, String lastName, String email, String address, String payment, String description, Menu menu) {
        super(firstName, lastName, email, address, "Chef");
        setPayment(payment);
        this.description = description;
        this.suspensionLength = "none";
        this.suspended = false;
        this.menu = menu;
        this.orders = new ArrayList<>();
        Order order = new Order(new Meal("IgnoreMe", "IgnoreMe", "0"), "0", "chefID", "clientID");
        orders.add(order);
        this.soldMeals = "0";
        this.rating = "-1";
    }

    public String getSuspensionLength() { return suspensionLength; }

    public void setSuspensionLength(String suspensionLength) { this.suspensionLength = suspensionLength; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Boolean getSuspended() { return this.suspended; }

    public void setIsSuspended(Boolean suspended) { this.suspended = suspended; }

    public Menu getMenu() { return menu; }

    public void setMenu(Menu menu) { this.menu = menu; }

    public List<Order> getOrders() { return orders; }

    public void setOrders(List<Order> orders) { this.orders = orders; }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public String getRating() { return rating; }

    public void setRating(String rating) { this.rating = rating; }

    public String getSoldMeals() { return soldMeals; }

    public void setSoldMeals(String soldMeals) { this.soldMeals = soldMeals; }
}
