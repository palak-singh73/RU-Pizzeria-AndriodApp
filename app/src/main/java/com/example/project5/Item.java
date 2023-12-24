package com.example.project5;

/**
 * Holds all the information that is to be displayed on the cards for recyclerview
 * Each card is to hold the speciality name, its image, type of sauce, and list of toppings
 * @author Palak Singh, Daniel Guan
 */
public class Item {

    private String pizzaType;
    private int image;
    private String price;
    private String toppingList;

    /**
     * Parameterized constructor for each item
     * @param pizzatype String that holds the speciality type name
     * @param image int position of pizza's image in the set array
     * @param price String price of the pizza
     * @param toppingList String list of toppings compiled in a String and separated using commas
     */
    public Item(String pizzatype, int image, String price, String toppingList) {
        this.pizzaType = pizzatype;
        this.image = image;
        this.price = price;
        this.toppingList = toppingList;
    }

    /**
     * Getter method that returns the pizza type of an pizza
     * @return String pizza type of an pizza
     */
    public String getType() {
        return pizzaType;
    }

    /**
     * Getter method that returns the image of an item.
     * @return int position of the image in a array
     */
    public int getImage() {
        return image;
    }

    /**
     * Getter method that returns kind of price the pizza uses
     * @return String of kind of price the pizza uses
     */
    public String getPrice() {
        return "$" + price;
    }

    /**
     * Getter method that returns list of toppings on the pizza
     * @return String list of toppings on the pizza
     */
    public String getToppingList() {
        return toppingList;
    }
}
