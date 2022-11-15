package com.example.mealer;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import com.example.mealer.structure.Meal;

import org.junit.Test;

public class MenuTesting {

    @Test
    public void verifyNotVegetarianMeal(){
        Meal meal= new Meal("poutine", "frites+fromage+sauce", "10dollars");
        Boolean isVegetarian = meal.getVegetarian();
        assertFalse("Verify that the meal is not vegetarian", isVegetarian);
    }

    @Test
    public void verifyVegetarianMeal(){
        Meal meal = new Meal("Salad", "Plain salad with cucumbers and tomatoes", "none");
        meal.setVegetarian(true);
        Boolean isVegetarian = meal.getVegetarian();
        assertTrue("Verify that the meal is vegetarian", isVegetarian);
    }

    @Test
    public void verifyUnavailableMeal(){
        Meal meal = new Meal("sushi", "rice+avocado+soyasauce", "25dollars");
        Boolean isAvailable = meal.getAvailable();
        assertFalse("Verify that the meal is unavailable", isAvailable);
    }

    @Test
    public void verifyAvailableMeal(){
        Meal meal = new Meal("salad", "tomatoes+onion+carrots", "5dollars");
        meal.setAvailable(true);
        Boolean isVegetarian = meal.getAvailable();
        assertTrue("Verify that the meal is available", isVegetarian);
    }
}
