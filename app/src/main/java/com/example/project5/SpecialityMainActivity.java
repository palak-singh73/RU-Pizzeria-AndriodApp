package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

/**
 * Populates the Speciality Recycler with all the data os speciality pizzas
 * @author Palak Singh, Daniel Guan
 */
public class SpecialityMainActivity extends AppCompatActivity {

    private ArrayList<Item> items = new ArrayList<>();
    private int [] itemImages = {R.drawable.deluxe, R.drawable.supreme, R.drawable.meatzza,
            R.drawable.seafood, R.drawable.pepperoni, R.drawable.veggiedelight, R.drawable.classichawaiian,
            R.drawable.margherita, R.drawable.sixcheese, R.drawable.spinachfeta};

    /**
     * Finds and sets up the recycler view
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sp_recycler);
        RecyclerView rcview = findViewById(R.id.rcView_menu);
        setupMenuItems();
        ItemsAdapter adapter = new ItemsAdapter(this, items);
        rcview.setAdapter(adapter);
        rcview.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Helper method to set up the data
     */
    private void setupMenuItems() {
        String [] specialityNames = getResources().getStringArray(R.array.specialityNames);
        for (int i = 0; i < specialityNames.length; i++) {
            Pizza settingPizza = PizzaMaker.createPizza(specialityNames[i]);
            String toppingslist = "";
            for(int j = 0; j < settingPizza.toppings.size() - 1; j++){
                toppingslist += settingPizza.toppings.get(j).toString() + ", ";
            }
            toppingslist += settingPizza.toppings.get(settingPizza.toppings.size() - 1).toString();
            items.add(new Item(settingPizza.getType(), itemImages[i], String.valueOf(settingPizza.price()), toppingslist));
        }
    }

}