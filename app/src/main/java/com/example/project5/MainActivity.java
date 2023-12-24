package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Serves as the entry point for the app
 * Gives the user 4 options - SP, BYO, Current Order, Store Order
 * @author Daniel Guan, Palak Singh
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Creates and sets the main view
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    /**
     * A callback method executed right after onCreate().
     */
    protected void onStart() {
        super.onStart();
        System.out.println("onStart() executed");
    }

    /**
     * A callback method executed right after onStart().
     */
    protected void onResume() {
        super.onResume();
        System.out.println("onResume() executed");
    }

    /**
     * Used to show the speciality ordering activity and view
     * @param view view from which the method is called
     */
    public void showSPOrdering(View view){
        Intent intent = new Intent(view.getContext(), SpecialityMainActivity.class);
        startActivity(intent);
    }

    /**
     * Used to show the BYO ordering activity and view
     * @param view view from which the method is called
     */
    public void showBYOOrdering(View view){
        Intent intent = new Intent(view.getContext(), BuildYourOwnActivity.class);
        startActivity(intent);
    }

    /**
     * Used to show the current order activity and view
     * @param view view from which the method is called
     */
    public void showOrderView(View view){
        Intent intent = new Intent(view.getContext(), CurrentOrderActivity.class);
        startActivity(intent);
    }

    /**
     * Used to show the store order activity and view
     * @param view view from which the method is called
     */
    public void showStoreOrder(View view){
        Intent intent = new Intent(view.getContext(), StoreOrderActivity.class);
        startActivity(intent);
    }
}