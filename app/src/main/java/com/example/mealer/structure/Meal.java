package com.example.mealer.structure;

public class Meal {
    private String mealName;
    private String mealDescription;
    private String mealPrice;
    private Boolean isVegetarian;
    private Boolean available;
//    private String menuID;

    public Meal() {}

    public Meal(String mealName, String mealDescription, String mealPrice) {
        this.mealName = mealName;
        this.mealDescription = mealDescription;
        this.mealPrice = mealPrice;
        setVegetarian(false);
        setAvailable(false);
    }

    public String getMealName() { return mealName; }
    public String getMealDescription() { return mealDescription; }
    public String getMealPrice() { return mealPrice; }
    public Boolean getVegetarian() { return isVegetarian; }
    public Boolean getAvailable() { return available; }
    public void setMealName(String mealName) { this.mealName = mealName; }
    public void setMealDescription(String mealDescription) { this.mealDescription = mealDescription; }
    public void setMealPrice(String mealPrice) { this.mealPrice = mealPrice; }
    public void setVegetarian(Boolean vegetarian) { isVegetarian = vegetarian; }
    public void setAvailable(Boolean available) { this.available = available; }
}
