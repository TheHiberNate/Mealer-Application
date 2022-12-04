package com.example.mealer.structure;

public class Order {
    private Meal meal;
    private String quantity;
    private String status;
    private String[] statusOptions = {"order sent to chef" , "order confirmed by chef",
            "order being prepared", "order on the way", "order delivered", "order rejected"};
    private String deliveryTime;
    private String chefID;
    private String clientID;

    public Order() { }

    public Order(Meal meal, String quantity, String chefID, String clientID) {
        this.meal = meal;
        this.quantity = quantity;
        this.status = statusOptions[0];
        this.deliveryTime = "none";
        this.chefID = chefID;
        this.clientID = clientID;
    }

    public Meal getMeal() { return meal; }
    public void setMeal(Meal meal) { this.meal = meal; }

    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String option) { this.status = option; }

    public String getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(String deliveryTime) { this.deliveryTime = deliveryTime; }

    public String getChefID() { return chefID; }
    public void setChefID(String chefID) { this.chefID = chefID; }

    public String getClientID() { return clientID; }
    public void setClientID(String clientID) { this.clientID = clientID; }
}
