package com.example.project5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Store Orders class keeps track of orders made by user
 * We can add, remove, and get an order, and export the orders to a txt file
 * @author Palak Singh, Daniel Guan
 */
public class StoreOrders {

    private static ArrayList<Orders> orders = new ArrayList<>();
    private static int next_order_number = 1;

    /**
     * Method to add an order to the Arraylist of orders
     * @param order to be added to the list
     */
    public static void add_order(Orders order){
        orders.add(order);
    }

    /**
     * Method to remove an order from the Arraylist of orders
     * @param ordNumRem of the order to be removed from the list
     */
    public static void remove_order(int ordNumRem){
        for (int i = 0; i < orders.size(); i++){
            if(orders.get(i).getOrderNumber() == ordNumRem){
                orders.remove(i);
                break;
            }
        }
        // orders.remove(ordNumRem);
    }

    /**
     * Helps to return a specific order from list of orders in StoreOrders
     * @param ordNumFind int for the specific order number you want to find
     * @return Order you wanted to find, if the order does not exist then it returns null
     */
    public static Orders get_order(int ordNumFind){
        for (int i = 0; i < orders.size(); i++){
            if(orders.get(i).getOrderNumber() == ordNumFind){
                return orders.get(i);
            }
        }
        return null;
    }

    /**
     * Getter method to retrieve the list of orders made
     * @return orders ArrayList of orders
     */
    public static ArrayList<Orders> getOrders(){
        return orders;
    }

    /**
     * Getter method for order number
     * @return order number int
     */
    public static int getnext_order_number(){
        return next_order_number;
    }

    /**
     * Method to increment the order number by 1
     */
    public static void incrementnext_order_number(){
        next_order_number++;
    }
}
