package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Handles all the activity for building your own pizza
 * @author Daniel Guan, Palak Singh
 */
public class StoreOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner soOrderNumSpinner;
    private ArrayAdapter<String> orderNumAdapter;
    private Button soCancelButton;
    private TextView soOrderTotalDisplay;
    private int soOrderNum;
    private ArrayList<String> pizzaList;
    private ArrayAdapter<String> pizzaListAdapter;
    private ListView soPizzaOrderList;
    private ArrayList<Integer> orderNums;

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
        setContentView(R.layout.store_order);
        setupOrderNumSpinner();
        soOrderTotalDisplay = findViewById(R.id.soOrderTotalDisplay);
        soOrderTotalDisplay.setText("");
    }

    /**
     * Sets up the spinner with all the order number current in Store Order
     */
    protected void setupOrderNumSpinner() {
        soOrderNumSpinner = findViewById(R.id.soOrderNumSpinner);
        orderNums = new ArrayList<>();
        for(int i = 0; i < StoreOrders.getOrders().size(); i++){
            orderNums.add(StoreOrders.getOrders().get(i).getOrderNumber());
        }
        orderNumAdapter = new ArrayAdapter(
                this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, orderNums);
        soOrderNumSpinner.setAdapter(orderNumAdapter);
        soOrderNumSpinner.setOnItemSelectedListener(this);
    }

    /**
     * Handles a specific order number selection from the spinner and views are changed to reflect that
     * @param parent The AdapterView where the selection happened
     * @param view The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        soOrderNum = Integer.parseInt(soOrderNumSpinner.getSelectedItem().toString());
        totalPrice();
        updatePizzaOrderList();
        soCancelButton = findViewById(R.id.soCancelButton);
        soCancelButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Remove button deleted the current order that is on the screen and updates the Store Order
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                StoreOrders.remove_order(soOrderNum);
                for(int i = 0; i < orderNums.size(); i++){
                    if(orderNums.get(i) == soOrderNum){
                        orderNums.remove(i);
                        if(orderNums.size() >= 1){
                            soOrderNum = orderNums.get(0);
                            soOrderNumSpinner.setSelection(0);
                        }else{
                            soOrderNum = -1;
                        }
                        updatePizzaOrderList();
                        totalPrice();
                        orderNumAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        });
    }

    /**
     * Called when no item is selected in the spinners
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /**
     * Displays the total price according to the order number selection
     */
    protected void totalPrice(){
        soOrderTotalDisplay.setText("");
        DecimalFormat df = new DecimalFormat("0.00");
        if(StoreOrders.get_order(soOrderNum) != null){
            String price = String.valueOf(df.format(StoreOrders.get_order(soOrderNum).getOrder_price()));
            soOrderTotalDisplay.setText(" $" + price);
        }
    }

    /**
     * Updates the views depending on the order number selected in the spinner
     */
    protected void updatePizzaOrderList(){
        pizzaList = new ArrayList<>();
        if(soOrderNum != -1) {
            for (Pizza piz : StoreOrders.get_order(soOrderNum).getPizzas()) {
                pizzaList.add(piz.toString());
            }
        }
        pizzaListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pizzaList);
        soPizzaOrderList = findViewById(R.id.soPizzaOrderList);
        soPizzaOrderList.setAdapter(pizzaListAdapter);
        pizzaListAdapter.notifyDataSetChanged();
    }
}