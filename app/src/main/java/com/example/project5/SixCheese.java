package com.example.project5;

import java.util.ArrayList;

/**
 * SixCheese class serves as a child class to Pizza class, lets the user create their SixCheese pizza
 * Can choose size, extraSauce, extraCheese
 * @author Palak Singh, Daniel Guan
 */
public class SixCheese extends Pizza{

    /**
     * Constructor of SixCheese pizza
     * Sets the sauce to tomato
     * Sets the toppings to three cheese blend, parmesan asiago, mozzarella
     */
    public SixCheese(){
        toppings = new ArrayList<>();
        this.sauce = Sauce.TOMATO;
        toppings.add(Topping.THREE_CHEESE_BLEND);
        toppings.add(Topping.PARMESAN_ASIAGO);
        toppings.add(Topping.MOZZARELLA);
    }

    /**
     * Calculates the price of a SixCheese pizza
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
     * Getter method that will return the type of pizza: Six Cheese
     * @return String "Six Cheese"
     */
    @Override
    public String getType() {
        return "Six Cheese";
    }
}
