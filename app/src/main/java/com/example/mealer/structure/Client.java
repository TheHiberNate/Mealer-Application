package com.example.mealer.structure;

import java.util.ArrayList;
import java.util.List;

public class Client extends User{
    private List<Order> orders;

    public Client() { }

    public Client(String firstName, String lastName, String email, String address, String payment) {
        super(firstName, lastName, email, address, "Client");
        setPayment(payment);
        this.orders = new ArrayList<>();
        Order order = new Order(new Meal("IgnoreMe", "IgnoreMe", "0"), "0", "chefID", "clientID");
        orders.add(order);
    }

    public List<Order> getOrders() { return orders; }

    public void setOrders(List<Order> orders) { this.orders = orders; }

    public void addOrder(Order order) {
        orders.add(order);
    }
}
