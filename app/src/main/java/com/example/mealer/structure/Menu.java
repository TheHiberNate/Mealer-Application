package com.example.mealer.structure;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private String title;
    private String description;
    private List<Meal> meals;

    public Menu() {}

    public Menu (String title, String description){
        this.title = title;
        this.description = description;
        meals = new ArrayList<>();
        addMeal(new Meal("Mexican","Italian Specialty", "45.0"));
        addMeal(new Meal("Greek","Greek Specialty", "25.0"));
    }

    public void addMeal(Meal meal) { meals.add(meal); }

    public void setDescription(String description){
        this.description=description;
    }
    public String getDescription(){
        return description;
    }
    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }
    public void setMeals(List<Meal> meals) { this.meals = meals; }
    public List<Meal> getMeals() { return meals; }
}
