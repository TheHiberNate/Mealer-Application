package com.example.mealer;

import static org.junit.Assert.assertEquals;

import com.example.mealer.structure.Chef;
import com.example.mealer.structure.Client;
import com.example.mealer.structure.Meal;
import com.example.mealer.structure.Menu;
import com.example.mealer.structure.Order;

import org.junit.Test;

public class OrderTesting {

    @Test
    public void verifyChefOrderQuantity() {
        Chef chef = new Chef("Bob", "Bob", "bob@bob.com", "Street", "1234", "description", new Menu());
        chef.addOrder(new Order(new Meal("Food", "Yummy", "100"), "3", "chefID", "clientID"));
        String expected = "3";
        String actual = chef.getOrders().get(1).getQuantity();
        assertEquals("Verify the meal quantity for the Order for the Chef", expected, actual);
    }

    @Test
    public void verifyClientOrderQuantity() {
        Client client = new Client("Bob", "Bob", "bob@bob.com", "Street", "1234");
        client.addOrder(new Order(new Meal("Food", "Yummy", "100"), "1000", "chefID", "clientID"));
        String expected = "1000";
        String actual = client.getOrders().get(1).getQuantity();
        assertEquals("Verify the meal quantity for the Order for the Client", expected, actual);
    }

    @Test
    public void verifyChefOrderChefID() {
        Chef chef = new Chef("Bob", "Bob", "bob@bob.com", "Street", "1234", "description", new Menu());
        chef.addOrder(new Order(new Meal("Food", "Yummy", "100"), "3", "chefID", "clientID"));
        String expected = "chefID";
        String actual = chef.getOrders().get(1).getChefID();
        assertEquals("Verify the ChefID for the Order for the Chef", expected, actual);
    }

    @Test
    public void verifyClientOrderChefID() {
        Client client = new Client("Bob", "Bob", "bob@bob.com", "Street", "1234");
        client.addOrder(new Order(new Meal("Food", "Yummy", "100"), "1000", "heakjads", "clientID"));
        String expected = "heakjads";
        String actual = client.getOrders().get(1).getChefID();
        assertEquals("Verify the ChefID for the Order for the Client", expected, actual);
    }

    @Test
    public void verifyChefOrderClientID() {
        Chef chef = new Chef("Bob", "Bob", "bob@bob.com", "Street", "1234", "description", new Menu());
        chef.addOrder(new Order(new Meal("Food", "Yummy", "100"), "3", "chefID", "qwerty"));
        String expected = "qwerty";
        String actual = chef.getOrders().get(1).getClientID();
        assertEquals("Verify the ClientID for the Order for the Chef", expected, actual);
    }

    @Test
    public void verifyClientOrderClientID() {
        Client client = new Client("Bob", "Bob", "bob@bob.com", "Street", "1234");
        client.addOrder(new Order(new Meal("Food", "Yummy", "100"), "1000", "chefID", "clientID"));
        String expected = "clientID";
        String actual = client.getOrders().get(1).getClientID();
        assertEquals("Verify the ClientID for the Order for the Client", expected, actual);
    }

    @Test
    public void verifyChefOrderMealName() {
        Chef chef = new Chef("Bob", "Bob", "bob@bob.com", "Street", "1234", "description", new Menu());
        chef.addOrder(new Order(new Meal("Lasagna", "Yummy", "100"), "3", "chefID", "qwerty"));
        String expected = "Lasagna";
        String actual = chef.getOrders().get(1).getMeal().getMealName();
        assertEquals("Verify the Meal Name for the Order for the Chef", expected, actual);
    }

    @Test
    public void verifyClientOrderMealName() {
        Client client = new Client("Bob", "Bob", "bob@bob.com", "Street", "1234");
        client.addOrder(new Order(new Meal("Ice Cream", "Yummy", "100"), "1000", "chefID", "clientID"));
        String expected = "Ice Cream";
        String actual = client.getOrders().get(1).getMeal().getMealName();
        assertEquals("Verify the Meal Name for the Order for the Client", expected, actual);
    }


}
