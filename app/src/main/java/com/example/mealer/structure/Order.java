package com.example.mealer.structure;

public class Order {
    private Meal meal;
    private String quantity;
    private String status;
    private String[] statusOptions = {"order sent to chef" , "order confirmed by chef",
            "order being prepared", "order on the way", "order delivered", "order rejected"};

    public Order() { }

    public Order(Meal meal, String quantity) {
        this.meal = meal;
        this.quantity = quantity;
        this.status = statusOptions[0];
    }

    public Meal getMeal() { return meal; }
    public void setMeal(Meal meal) { this.meal = meal; }

    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String option) { this.status = option; }
}
