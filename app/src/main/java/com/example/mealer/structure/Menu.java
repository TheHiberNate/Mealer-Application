package com.example.mealer.structure;

import java.util.ArrayList;
import java.util.List;

public class Menu {
//    private String title;
//    private String description;
    private List<Meal> meals;

    public Menu (){
        meals = new ArrayList<>();
        Meal meal = new Meal("NAME OF MEAL HERE", "Description of Meal (this is just a template of what a meal would look like, it will not be displayed on your menu)", "none");
        meal.setVegetarian(false);
        addMeal(meal);
    }

    public void addMeal(Meal meal) { meals.add(meal); }

//    public void setDescription(String description){
//        this.description=description;
//    }
//    public String getDescription(){
//        return description;
//    }
//    public void setTitle(String title) { this.title = title; }
//    public String getTitle() { return title; }
    public void setMeals(List<Meal> meals) { this.meals = meals; }
    public List<Meal> getMeals() { return meals; }
}
