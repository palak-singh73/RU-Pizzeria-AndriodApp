package com.example.project5;

import java.util.ArrayList;

/**
 * ClassicHawaiian class serves as a child class to Pizza class, lets the user create their Classic Hawaiian  pizza
 * Can choose size, extraSauce, extraCheese
 * @author Palak Singh, Daniel Guan
 */
public class ClassicHawaiian extends Pizza{

    /**
     * Constructor of Classic Hawaiian pizza
     * Sets the sauce to tomato
     * Sets the toppings to ham, pineapple, and green pepper
     */
    public ClassicHawaiian(){
        toppings = new ArrayList<>();
        this.sauce = Sauce.TOMATO;
        toppings.add(Topping.HAM);
        toppings.add(Topping.PINEAPPLE);
        toppings.add(Topping.GREEN_PEPPER);
    }

    /**
     * Calculates the price of a Hawaiian pizza
     * Takes into account the size, extraCheese, and extraSauce
     * @return double price of the pizza
     */
    @Override
    public double price() {
        double price = 12.99;
        if(size != null){
            if(size.equals(Size.MEDIUM)){
                price += 2;
            }if(size.equals(Size.LARGE)){
                price += 4;
            }
            if(extraSauce){
                price += 1;
            }
            if(extraCheese){
                price += 1;
            }
        }
        return price;
    }

    /**
     * Getter method that will return the type of pizza: Classic Hawaiian
     * @return String "Classic Hawaiian"
     */
    @Override
    public String getType() {
        return "Classic Hawaiian";
    }
}
