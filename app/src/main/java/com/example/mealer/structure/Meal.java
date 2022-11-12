package com.example.mealer.structure;

public class Meal {
    private String mealName;
    private String mealDescription;
    private String mealPrice;
    private Boolean isVegeterian;
    private Boolean available;
//    private String menuID;

    public Meal(String mealName, String mealDescription, String mealPrice) {
        this.mealName = mealName;
        this.mealDescription = mealDescription;
        this.mealPrice = mealPrice;
        setVegeterian(false);
        setAvailable(false);
    }


    public String getMealName() { return mealName; }
    public String getMealDescription() { return mealDescription; }
    public String getMealPrice() { return mealPrice; }
    public Boolean getVegeterian() { return isVegeterian; }
    public Boolean getAvailable() { return available; }
    public void setMealName(String mealName) { this.mealName = mealName; }
    public void setMealDescription(String mealDescription) { this.mealDescription = mealDescription; }
    public void setMealPrice(String mealPrice) { this.mealPrice = mealPrice; }
    public void setVegeterian(Boolean vegeterian) { isVegeterian = vegeterian; }
    public void setAvailable(Boolean available) { this.available = available; }
}
