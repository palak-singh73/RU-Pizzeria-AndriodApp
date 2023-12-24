package com.example.project5;

import java.util.ArrayList;

/**
 * Margherita class serves as a child class to Pizza class, lets the user create their Margherita pizza
 * Can choose size, extraSauce, extraCheese
 * @author Palak Singh, Daniel Guan
 */
public class Margherita extends Pizza{

    /**
     * Constructor of Margherita pizza
     * Sets the sauce to tomato
     * Sets the toppings to tomato, mozzarella, basil
     */
    public Margherita(){
        toppings = new ArrayList<>();
        this.sauce = Sauce.TOMATO;
        toppings.add(Topping.TOMATO);
        toppings.add(Topping.MOZZARELLA);
        toppings.add(Topping.BASIL);
    }

    /**
     * Calculates the price of a margherita pizza
     * Takes into account the size, extraCheese, and extraSauce
     * @return double price of the pizza
     */
    @Override
    public double price() {
        double price = 10.99;
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
     * Getter method that will return the type of pizza: Margherita
     * @return String "Margherita"
     */
    @Override
    public String getType() {
        return "Margherita";
    }
}
