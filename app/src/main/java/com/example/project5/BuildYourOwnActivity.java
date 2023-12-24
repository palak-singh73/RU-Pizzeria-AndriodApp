package com.example.project5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Handles all the activity for building your own pizza
 * @author Palak Singh, Daniel Guan
 */
public class BuildYourOwnActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner SizeSpinner, SauceSpinner;
    private ArrayAdapter<String> sizeadapter, sauceadapter;
    private ListView AvailableToppingsList, SelectedToppingsList;
    private String [] toppingsAva;
    private ArrayAdapter<String> toppingAvaAdapter, toppingSelAdapter;
    private CheckBox BYOExtraSauceCheck, BYOExtraCheeseCheck;
    private TextView BYOPriceDisplay;
    private Button BYOAddToOrderButton;
    private ArrayList<String> toppingToAdd, selectedToppings;
    private Pizza currentPie;
    private ArrayList<Topping> toppingsToAddToPie;
    private String size, sauce;
    private boolean extraSauce, extraCheese;

    /**
     * Sets the main view and runs all methods associated with the activity
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.byo_ordering);
        currentPie = PizzaMaker.createPizza("Build Your Own");
        toppingsToAddToPie = new ArrayList<>();
        settingSpinners();
        populateAvailableToppings();
        extrasAdded();
        addToOrder();
    }

    /**
     * Gets the selection from both the spinners
     * @param parent The AdapterView where the selection happened
     * @param view The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        size = SizeSpinner.getSelectedItem().toString();
        sauce = SauceSpinner.getSelectedItem().toString();
        pricing();
    }

    /**
     * Called when no item is selected in the spinners
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /**
     * Populates both spinners with respective values
     */
    protected void settingSpinners(){
        SizeSpinner = findViewById(R.id.SizeSpinner);
        String[] sizes = {"Choose a Size", "Small", "Medium", "Large"};
        sizeadapter = new ArrayAdapter(
                this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sizes);
        SizeSpinner.setAdapter(sizeadapter);
        SizeSpinner.setOnItemSelectedListener(this);

        SauceSpinner = findViewById(R.id.SauceSpinner);
        String[] sauces = {"Choose a Sauce", "TOMATO", "ALFREDO"};
        sauceadapter = new ArrayAdapter(
                this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sauces);
        SauceSpinner.setAdapter(sauceadapter);
        SauceSpinner.setOnItemSelectedListener(this);
    }

    /**
     * Adds toppings to the available and selected toppings listview
     */
    protected void populateAvailableToppings(){
        settingInitialListViews();
        fixListLayout();
        AvailableToppingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * When you click on a topping on Additional Topping list, its removes it from that and adds it to Selected List
             * @param parent The AdapterView where the click happened.
             * @param view The view within the AdapterView that was clicked (this
             *            will be a view provided by the adapter)
             * @param position The position of the view in the adapter.
             * @param id The row id of the item that was clicked.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(toppingsToAddToPie.size() < 7){
                    String topping = toppingAvaAdapter.getItem(position);
                    addingTopping(topping);
                    toppingToAdd.remove(position);
                    selectedToppings.add(topping);
                    fixListLayout();
                    toppingAvaAdapter.notifyDataSetChanged();
                    toppingSelAdapter.notifyDataSetChanged();
                    pricing();
                }else{
                    moreThan7ToppingsErrorMessage();
                }
            }
        });
        SelectedToppingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * When you click on a topping on Selected Topping list, its removes it from that and adds it to Additional List
             * @param parent The AdapterView where the click happened.
             * @param view The view within the AdapterView that was clicked (this
             *            will be a view provided by the adapter)
             * @param position The position of the view in the adapter.
             * @param id The row id of the item that was clicked.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String topping = toppingSelAdapter.getItem(position);
                removingTopping(topping);
                selectedToppings.remove(position);
                toppingToAdd.add(topping);
                fixListLayout();
                toppingAvaAdapter.notifyDataSetChanged();
                toppingSelAdapter.notifyDataSetChanged();
                pricing();
            }
        });
    }

    /**
     * Sets all the listviews with its their adapters
     */
    private void settingInitialListViews(){
        toppingToAdd = new ArrayList<>();
        selectedToppings = new ArrayList<>();
        for(Topping t : Topping.values()){
            toppingToAdd.add(t.toString());
        }
        toppingAvaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toppingToAdd);
        AvailableToppingsList = findViewById(R.id.AvaliableToppingsList);
        toppingSelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, selectedToppings);
        SelectedToppingsList = findViewById(R.id.SelectedToppingsList);
        AvailableToppingsList.setAdapter(toppingAvaAdapter);
        SelectedToppingsList.setAdapter(toppingSelAdapter);
    }

    /**
     * Resets the height of the listviews so that you can see all the toppings at once
     */
    protected void fixListLayout(){
        ViewGroup.LayoutParams paramAva = AvailableToppingsList.getLayoutParams();
        View viewAva = toppingAvaAdapter.getView(0, null, AvailableToppingsList);
        viewAva.measure(View.MeasureSpec.makeMeasureSpec(paramAva.width, View.MeasureSpec.EXACTLY), 0);
        paramAva.height = viewAva.getMeasuredHeight() * (toppingToAdd.size()+1);
        AvailableToppingsList.setLayoutParams(paramAva);
        if (toppingsToAddToPie.size() != 0){
            ViewGroup.LayoutParams paramsSel = SelectedToppingsList.getLayoutParams();
            View view2Sel = toppingSelAdapter.getView(0, null, SelectedToppingsList);
            view2Sel.measure(View.MeasureSpec.makeMeasureSpec(paramsSel.width, View.MeasureSpec.EXACTLY), 0);
            paramsSel.height = view2Sel.getMeasuredHeight() * (selectedToppings.size()+1);
            SelectedToppingsList.setLayoutParams(paramsSel);
        }
    }

    /**
     * Adds the topping to the pizza's topping arraylist
     * @param topping String name of the topping we are going to be adding to the pizza
     */
    protected void addingTopping(String topping){
        for(Topping t : Topping.values()){
            if(t.toString().equalsIgnoreCase(topping)){
                toppingsToAddToPie.add(t);
            }
        }
    }

    /**
     * Remove the topping to the pizza's topping arraylist
     * @param topping String name of the topping we want to remove for our selection
     */
    protected void removingTopping(String topping){
        for(Topping t : Topping.values()){
            if(t.toString().equalsIgnoreCase(topping)){
                toppingsToAddToPie.remove(t);
            }
        }
    }

    /**
     * Error message that pops up when you try to add more than 7 toppings
     */
    protected void moreThan7ToppingsErrorMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert: Invalid Addition");
        builder.setMessage("You cannot add more than 7 Toppings on Your Pizza.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Checks to see if extraSauce or extraCheese is added to the pizza
     */
    protected void extrasAdded(){
        BYOExtraSauceCheck = findViewById(R.id.BYOExtraSauceCheck);
        BYOExtraCheeseCheck = findViewById(R.id.BYOExtraCheeseCheck);
        BYOExtraSauceCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * Checks to see if extraSauce is checked off
             * @param buttonView The compound button view whose state has changed.
             * @param isChecked  The new checked state of buttonView.
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    extraSauce = true;
                    pricing();
                } else {
                    extraSauce = false;
                    pricing();

                }
            }
        });
        BYOExtraCheeseCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * Checks to see if extraCheese is checked off
             * @param buttonView The compound button view whose state has changed.
             * @param isChecked  The new checked state of buttonView.
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    extraCheese = true;
                    pricing();
                } else {
                    extraCheese = false;
                    pricing();
                }
            }
        });
    }

    /**
     * Builds the pizza according to the user selections
     */
    protected void buildThePie(){
        currentPie.setSize(size);
        currentPie.pickSauce(sauce);
        currentPie.addTopping(toppingsToAddToPie);
        currentPie.setExtraSauce(extraSauce);
        currentPie.setExtraCheese(extraCheese);
    }

    /**
     * Gets the price of the pizza that was build
     */
    protected void pricing(){
        BYOPriceDisplay = findViewById(R.id.BYOPriceDisplay);
        BYOPriceDisplay.setText("");
        if(!size.equalsIgnoreCase("Choose a Size") && !sauce.equalsIgnoreCase("Choose a Sauce")){
            buildThePie();
            DecimalFormat df = new DecimalFormat("0.00");
            String price = String.valueOf(df.format(currentPie.price()));
            BYOPriceDisplay.setText(" $" + price);
        }
    }

    /**
     * Adds the user pizza to the order with button is clicked
     */
    protected void addToOrder(){
        BYOAddToOrderButton = findViewById(R.id.BYOAddToOrderButton);
        BYOAddToOrderButton.setOnClickListener(new View.OnClickListener() {
            /**
             * When the button is clicked, as long as everything necessary is selected, its adds the pizza to order
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                if(!size.equalsIgnoreCase("Choose a Size") && !sauce.equalsIgnoreCase("Choose a Sauce") && (toppingsToAddToPie.size() >= 3)){
                    buildThePie();
                    addingPizzaConfirmation();
                }else{
                    cannotAddToOrderAlert();
                }
            }
        });
    }

    /**
     * Error message if not everything necessary is entered
     */
    protected void cannotAddToOrderAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert: Invalid Pizza");
        builder.setMessage("Make sure that you have selected a size and a sauce and at least three toppings.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            /**
             * The alert is dismissed once acknowledged
             * @param dialog the dialog that received the click
             * @param which the button that was clicked (ex.
             *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
             *              of the item clicked
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Confirmation message before pizza is added
     */
    protected void addingPizzaConfirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to add the following pizza to your current order?");
        builder.setMessage(currentPie.toString());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            /**
             * Toast appears once the pizza is successfully added
             * @param dialog the dialog that received the click
             * @param which the button that was clicked (ex.
             *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
             *              of the item clicked
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Pizza Added to Order", Toast.LENGTH_LONG).show();
                Orders.addPizzaToOrder(currentPie);
                finish();
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            /**
             * User can go back to editing the pizza, toast appears to inform the user
             * @param dialog the dialog that received the click
             * @param which the button that was clicked (ex.
             *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
             *              of the item clicked
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You are back to editing your pizza", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}