package com.example.project5;

import java.util.ArrayList;

/**
 * VeggieDelight class serves as a child class to Pizza class, lets the user create their Veggie pizza
 * Can choose size, extraSauce, extraCheese
 * @author Palak Singh, Daniel Guan
 */
public class VeggieDelight extends Pizza{

    /**
     * Constructor of Veggie pizza
     * Sets the sauce to tomato
     * Sets the toppings to green pepper, onion, black olive, mushroom, jalapeno peppers, tomato
     */
    public VeggieDelight(){
        toppings = new ArrayList<>();
        this.sauce = Sauce.TOMATO;
        toppings.add(Topping.GREEN_PEPPER);
        toppings.add(Topping.ONION);
        toppings.add(Topping.BLACK_OLIVE);
        toppings.add(Topping.MUSHROOM);
        toppings.add(Topping.JALAPENO_PEPPER);
        toppings.add(Topping.TOMATO);
    }

    /**
     * Calculates the price of a veggie pizza
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
     * Getter method that will return the type of pizza: Veggie Delight
     * @return String "Veggie Delight"
     */
    @Override
    public String getType() {
        return "Veggie Delight";
    }
}
