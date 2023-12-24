package com.example.project5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Handles all the activity for current order view
 * @author Daniel Guan, Palak Singh
 */
public class CurrentOrderActivity extends AppCompatActivity {
    private Button placeOrderButton;
    private Button removePizzaButton;
    private TextView orderNumDisplay;
    private TextView orderTotalDisplay;
    private TextView subTotalDisplay;
    private TextView salesTaxDisplay;
    private ListView pizzaOrderList;
    private ArrayAdapter<String> pizzaListAdapter;
    private ArrayList<String> pizzaList;

    /**
     * Sets the main view and runs all methods associated with the activity
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_view);
        orderNumDisplay = findViewById(R.id.orderNumDisplay);
        orderTotalDisplay = findViewById(R.id.orderTotalDisplay);
        subTotalDisplay = findViewById(R.id.subTotalDisplay);
        salesTaxDisplay = findViewById(R.id.salesTaxDisplay);
        orderNumDisplay.setText(String.valueOf(Orders.getNextOrderNumber()));
        setCosts();
        removePizza();
        placeOrder();
    }

    /**
     * Displays the subtotal, sales, tax amount, and the order total amount on the textView
     */
    private void setCosts(){
        DecimalFormat df = new DecimalFormat("0.00");
        subTotalDisplay.setText("$" + String.valueOf(df.format(Orders.getTotal())));
        salesTaxDisplay.setText("$" + String.valueOf(df.format(Orders.getTotal() * 0.06625)));
        orderTotalDisplay.setText("$" + String.valueOf(df.format(Orders.getTotal() * 1.06625)));
    }

    /**
     * Let's the user remove a specific pizza from the order
     */
    protected void removePizza() {
        pizzaList = Orders.getPizzaArrayList();
        pizzaListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pizzaList);
        pizzaOrderList = findViewById(R.id.pizzaOrderList);
        pizzaOrderList.setAdapter(pizzaListAdapter);
        fixingLayout();
        pizzaOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Once an item from listview is selected, a toast appears allowing the user to remove the pizza from order
             * @param parent The AdapterView where the click happened.
             * @param view The view within the AdapterView that was clicked (this
             *            will be a view provided by the adapter)
             * @param position The position of the view in the adapter.
             * @param id The row id of the item that was clicked.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Pizza Selected. Ready to be removed when the button is clicked.", Toast.LENGTH_LONG).show();
                removePizzaButton = findViewById(R.id.removePizzaButton);
                removePizzaButton.setOnClickListener(new View.OnClickListener() {
                    /**
                     * Removes the selected pizza from order, if no pizza selected it removes the first one on the list
                     * @param v The view that was clicked.
                     */
                    @Override
                    public void onClick(View v) {
                        String pizzaToRemove = pizzaListAdapter.getItem(position);
                        Orders.removePizzaFromOrder(pizzaToRemove);
                        pizzaList.remove(position);
                        fixingLayout();
                        pizzaListAdapter.notifyDataSetChanged();
                        setCosts();
                    }
                });
            }
        });
    }

    /**
     * Sets the height for the listview according to how many pizzas there are
     */
    protected void fixingLayout(){
        if(Orders.getPizzaArrayList().size() != 0){
            ViewGroup.LayoutParams paramAva = pizzaOrderList.getLayoutParams();
            View viewAva = pizzaListAdapter.getView(0, null, pizzaOrderList);
            viewAva.measure(View.MeasureSpec.makeMeasureSpec(paramAva.width, View.MeasureSpec.EXACTLY), 0);
            paramAva.height = viewAva.getMeasuredHeight() * (pizzaList.size()+1);
            pizzaOrderList.setLayoutParams(paramAva);
        }

    }

    /**
     * As long as there is at least one pizza in the order, the user can place an order to the store
     */
    protected void placeOrder(){
        placeOrderButton = findViewById(R.id.placeOrderButton);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Orders.getPizzaArrayList().size() > 0){
                    orderPlacedConfirmation();
                }else{
                    cannotPlaceOrderAlert();
                }
            }
        });
    }

    /**
     * Confirmation message before an order is places to store
     */
    protected void orderPlacedConfirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to place order?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            /**
             * Order is sent to Store Orders adn toast appears letting the user know
             * @param dialog the dialog that received the click
             * @param which the button that was clicked (ex.
             *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
             *              of the item clicked
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Order Placed", Toast.LENGTH_LONG).show();
                Orders order = new Orders();
                StoreOrders.add_order(order);
                Orders.resetOrderList();
                Orders.resetOrderSubtotal();
                pizzaList.clear();
                setCosts();
                StoreOrders.incrementnext_order_number();
                orderNumDisplay.setText(String.valueOf(Orders.getNextOrderNumber()));
                pizzaListAdapter.notifyDataSetChanged();
                finish();
//                dialog.dismiss();
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            /**
             * Doesn't place the order and takes you back to the current order
             * @param dialog the dialog that received the click
             * @param which the button that was clicked (ex.
             *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
             *              of the item clicked
             */
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You are back to editing your order", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Error message is the user tries to place an empty order (aka no pizzas)
     */
    protected void cannotPlaceOrderAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert: Invalid Order");
        builder.setMessage("Make sure that you ordered pizzas before placing an order.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}