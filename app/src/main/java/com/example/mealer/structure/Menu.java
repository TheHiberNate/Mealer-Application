package com.example.mealer.structure;

import java.util.ArrayList.*;

public class Menu {
    String name;
    float price;
    String description;


    public Menu (String name, float price, String description){
        this.name=name;
        this.price=price;
        this.description=description;
    }

    public void setDescription(String description){
        this.description=description;
    }
    public String getDescription(){
        return description;
    }

    public void setPrice(float price){
        this.price=price;
    }
    public float getPrice(){
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}
