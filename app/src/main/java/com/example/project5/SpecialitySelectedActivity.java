package com.example.project5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Handles all the activity for once a specific speciality pizza is selected
 * @author Daniel Guan, Palak Singh
 */
public class SpecialitySelectedActivity extends AppCompatActivity{

    private TextView SPTitleText, SPSauceSelection, SPPriceDisplay;
    private ListView SPToppings;
    private ArrayAdapter<String> toppingadapter;
    private ImageView SpecialityImage;
    private Pizza currentPizza;
    private RadioButton SPSmallButton, SPMediumButton, SPLargeButton;
    private CheckBox SPExtraSauceCheck, SPExtraCheeseCheck;
    private Button SPAddtoOrder;
    private String size;
    private boolean extraSauce, extraCheese;
    private EditText quantityInput;
    private int quantity;

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
        setContentView(R.layout.speciality_selected_view);
        SPTitleText = findViewById(R.id.SPTitleText);
        Intent intent = getIntent();
        SPTitleText.setText(intent.getStringExtra("TYPE"));
        currentPizza = PizzaMaker.createPizza(Objects.requireNonNull(intent.getStringExtra("TYPE")));
        changeImage(currentPizza.getType());
        setToppings(currentPizza.getType());
        SPSauceSelection = findViewById(R.id.SPSauceSelection);
        SPSauceSelection.setText(currentPizza.sauce.name() + " SAUCE");
        sizeChanged();
        extrasAdded();
        setQuantity();
        addToOrder();
    }

    /**
     * Changes the image with the image of the speciality pizza
     * @param type String name of the speciality
     */
    protected void changeImage(String type){
        SpecialityImage = findViewById(R.id.SpecialityImage);
        if(type.equalsIgnoreCase("Deluxe")){
            SpecialityImage.setImageResource(R.drawable.deluxe);
        }else if(type.equalsIgnoreCase("Supreme")){
            SpecialityImage.setImageResource(R.drawable.supreme);
        }else if(type.equalsIgnoreCase("Meatzza")){
            SpecialityImage.setImageResource(R.drawable.meatzza);
        }else if(type.equalsIgnoreCase("Seafood")){
            SpecialityImage.setImageResource(R.drawable.seafood);
        }else if(type.equalsIgnoreCase("Pepperoni")){
            SpecialityImage.setImageResource(R.drawable.pepperoni);
        }else if(type.equalsIgnoreCase("Veggie Delight")){
            SpecialityImage.setImageResource(R.drawable.veggiedelight);
        }else if(type.equalsIgnoreCase("Classic Hawaiian")){
            SpecialityImage.setImageResource(R.drawable.classichawaiian);
        }else if(type.equalsIgnoreCase("Margherita")){
            SpecialityImage.setImageResource(R.drawable.margherita);
        }else if(type.equalsIgnoreCase("Six Cheese")){
            SpecialityImage.setImageResource(R.drawable.sixcheese);
        }else if(type.equalsIgnoreCase("Spinach Feta")){
            SpecialityImage.setImageResource(R.drawable.spinachfeta);
        }
    }

    /**
     * Populates the listview with the predetermined toppings for each speciality
     * @param type String name of the speciality
     */
    protected void setToppings(String type){
        ArrayList<String> toppingToAdd = new ArrayList<>();
        Pizza currentPie = PizzaMaker.createPizza(type);
        for(int i = 0; i < currentPie.toppings.size(); i++){
            toppingToAdd.add(currentPie.toppings.get(i).toString());
        }
        String[] toppings = toppingToAdd.toArray(new String[0]);
        toppingadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toppings);
        SPToppings = findViewById(R.id.SPToppings);
        SPToppings.setAdapter(toppingadapter);
        ViewGroup.LayoutParams params = SPToppings.getLayoutParams();
        View view = toppingadapter.getView(0, null, SPToppings);
        view.measure(View.MeasureSpec.makeMeasureSpec(params.width, View.MeasureSpec.EXACTLY), 0);
        params.height = view.getMeasuredHeight() * toppingToAdd.size();
        SPToppings.setLayoutParams(params);
    }

    /**
     * Sets up the radio buttons for size and changes the size when one is selected
     */
    protected void sizeChanged(){
        SPSmallButton = findViewById(R.id.SPSmallButton);
        SPSmallButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * Sets the size to small if small button is selected and fixes the pricing
             * @param buttonView The compound button view whose state has changed.
             * @param isChecked  The new checked state of buttonView.
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    size = "small";
                    pricing();
                }
            }
        });
        SPMediumButton = findViewById(R.id.SPMediumButton);
        SPMediumButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * Sets the size to medium if medium button is selected and fixes the pricing
             * @param buttonView The compound button view whose state has changed.
             * @param isChecked  The new checked state of buttonView.
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    size = "medium";
                    pricing();
                }
            }
        });
        SPLargeButton = findViewById(R.id.SPLargeButton);
        SPLargeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * Sets the size to large if large button is selected and fixes the pricing
             * @param buttonView The compound button view whose state has changed.
             * @param isChecked  The new checked state of buttonView.
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    size = "large";
                    pricing();
                }
            }
        });
    }

    /**
     * Sets up the checkboxes for extraSauce and extraCheese and changes price accordingly
     */
    protected void extrasAdded(){
        SPExtraSauceCheck = findViewById(R.id.SPExtraSauceCheck);
        SPExtraCheeseCheck = findViewById(R.id.SPExtraCheeseCheck);
        SPExtraSauceCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * Checks to see if the extraSauce is selected and fixes the price
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
        SPExtraCheeseCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * Checks to see if the extraCheese is selected and fixes the price
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
     * Sets the quantity for the pizza
     * If no quantity is entered then sets the quantity to 0
     * Includes the listener for the EditText
     */
    protected void setQuantity(){
        quantityInput = findViewById(R.id.quantityInput);
        quantityInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!quantityInput.getText().toString().isEmpty()){
                    quantity = Integer.parseInt(quantityInput.getText().toString());
                }else{
                    quantity = 0;
                }
                pricing();
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!quantityInput.getText().toString().isEmpty()){
                    quantity = Integer.parseInt(quantityInput.getText().toString());
                }else{
                    quantity = 0;
                }
                pricing();
            }
        });
    }

    /**
     * Builds the pizza according to the user selections
     */
    protected void buildThePie(){
        currentPizza.setSize(size);
        currentPizza.setExtraSauce(extraSauce);
        currentPizza.setExtraCheese(extraCheese);
    }

    /**
     * Gets the price of the pizza that was build
     */
    protected void pricing(){
        SPPriceDisplay = findViewById(R.id.SPPriceDisplay);
        SPPriceDisplay.setText("");
        if((size != null) && (quantity != 0)){
            buildThePie();
            DecimalFormat df = new DecimalFormat("0.00");
            String price = String.valueOf(df.format(currentPizza.price() * quantity));
            SPPriceDisplay.setText(" $" + price);
        }
    }

    /**
     * Adds the user pizza to the order with button is clicked
     */
    protected void addToOrder(){
        SPAddtoOrder = findViewById(R.id.SPAddtoOrder);
        SPAddtoOrder.setOnClickListener(new View.OnClickListener() {
            /**
             * When the button is clicked, as long as everything necessary is selected, its adds the pizza to order
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                if((size != null) && (quantity != 0)){
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
        builder.setMessage("Make sure that you have selected a size and quantity.");
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
        builder.setMessage(currentPizza.toString() + "\nQnty: " + quantity);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            /**
             * Toast appears once the pizza(s) is successfully added
             * @param dialog the dialog that received the click
             * @param which the button that was clicked (ex.
             *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
             *              of the item clicked
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Pizza Added to Order", Toast.LENGTH_LONG).show();
                for(int i = 0; i < quantity; i++){
                    Orders.addPizzaToOrder(currentPizza);
                }
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