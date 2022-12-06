package com.example.mealer.structure;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private String mealName;
    private String mealDescription;
    private String mealPrice;
    private Boolean vegetarian;
    private Boolean available;
    private String rating;
    private List<Order> orders;

    public Meal() {}

    public Meal(String mealName, String mealDescription, String mealPrice) {
        this.mealName = mealName;
        this.mealDescription = mealDescription;
        this.mealPrice = mealPrice;
        setVegetarian(false);
        setAvailable(false);
        setRating("-1"); // -1 is for no ratings
        this.orders = new ArrayList<>();
    }

    public String getMealName() { return mealName; }
    public String getMealDescription() { return mealDescription; }
    public String getMealPrice() { return mealPrice; }
    public Boolean getVegetarian() { return vegetarian; }
    public Boolean getAvailable() { return available; }
    public String getRating() { return rating; }
    public List<Order> getOrders() { return orders; }

    public void setMealName(String mealName) { this.mealName = mealName; }
    public void setMealDescription(String mealDescription) { this.mealDescription = mealDescription; }
    public void setMealPrice(String mealPrice) { this.mealPrice = mealPrice; }
    public void setVegetarian(Boolean vegetarian) { this.vegetarian = vegetarian; }
    public void setAvailable(Boolean available) { this.available = available; }
    public void setRating(String rating) { this.rating = rating; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
}
