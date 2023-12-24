package com.example.project5;

import java.util.ArrayList;

/**
 * SpinachFeta class serves as a child class to Pizza class, lets the user create their Spinach Feta pizza
 * Can choose size, extraSauce, extraCheese
 * @author Palak Singh, Daniel Guan
 */
public class SpinachFeta extends Pizza{

    /**
     * Constructor of SpinachFeta pizza
     * Sets the sauce to alfredo
     * Sets the toppings to spinach, feta, onion, and parmesan asiago
     */
    public SpinachFeta(){
        toppings = new ArrayList<>();
        this.sauce = Sauce.ALFREDO;
        toppings.add(Topping.SPINACH);
        toppings.add(Topping.FETA);
        toppings.add(Topping.ONION);
        toppings.add(Topping.PARMESAN_ASIAGO);
    }

    /**
     * Calculates the price of a SpinachFeta pizza
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
     * Getter method that will return the type of pizza: Spinach Feta
     * @return String "Spinach Feta"
     */
    @Override
    public String getType() {
        return "Spinach Feta";
    }
}
