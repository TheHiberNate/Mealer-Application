package com.example.mealer.structure;

public class Order {
    private Meal meal;
    private int quantity;
    private String status;
    private String[] statusOptions = {"order sent to chef" , "order confirmed", "order being prepared",
            "order on the way", "order delivered", "order rejected"};

    public Order() { }

    public Order(Meal meal, int quantity) {
        this.meal = meal;
        this.quantity = quantity;
        this.status = statusOptions[0];
    }

    public Meal getMeal() { return meal; }
    public void setMeal(Meal meal) { this.meal = meal; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(int option) { this.status = statusOptions[option]; }
}
