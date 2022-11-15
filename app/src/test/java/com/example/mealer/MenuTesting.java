package com.example.mealer;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import com.example.mealer.structure.Meal;

import org.junit.Test;

public class MenuTesting {

    @Test
    public void verifyMeal(){
        Meal meal= new Meal("poutine", "frites+fromage+sauce", "10dollars");
        Boolean isVegetarian=meal.getVegetarian();
        assertTrue("the meal is vegetarian", isVegetarian);
    }

    @Test
    public void verifyAvailableMeal(){
        Meal meal=new Meal("sushi", "rice+avocado+soyasauce", "25dollars");
        Boolean isAvailable=meal.getAvailable();
        assertFalse("the meal name is not available", isAvailable);
    }
}
