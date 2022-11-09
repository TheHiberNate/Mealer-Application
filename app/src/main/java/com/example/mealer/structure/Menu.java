package com.example.mealer.structure;

import java.util.ArrayList.*;

public class Menu {
    private String title;
    private double price;
    private String description;


    public Menu (String title, double price, String description){
        this.title = title;
        this.price=price;
        this.description=description;
    }

    public void setDescription(String description){
        this.description=description;
    }
    public String getDescription(){
        return description;
    }

    public void setPrice(double price){
        this.price=price;
    }
    public double getPrice(){
        return price;
    }

    public void setName(String name) {
        this.title = name;
    }
    public String getName() {
        return title;
    }

}
