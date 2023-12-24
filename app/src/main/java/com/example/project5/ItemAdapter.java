package com.example.project5;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Used to instantiate an adapter for the RecyclerView
 * @author Palak Singh, Daniel Guan
 */
class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder>{

    private Context context;
    private ArrayList<Item> items;

    /**
     * Constructor for the ItemsAdapter
     * @param context of the application.
     * @param items list of items to be displayed
     */
    public ItemsAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    /**
     * This method will inflate the row layout for the items in the RecyclerView
     * @param parent ViewGroup that contains the RecyclerView
     * @param viewType type of the view
     * @return ItemsHolder which is a ViewHolder that holds the inflated view
     */
    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.speciality_card, parent, false);
        return new ItemsHolder(view);
    }


    /**
     * Assign data values for each row as the item becomes visible on the screen
     * @param holder which is an instance of ItemsHolder
     * @param position index of the item in the list of items
     */
    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position) {
        holder.spName.setText(items.get(position).getType());
        holder.spPrice.setText(items.get(position).getPrice());
        holder.toppingsOnSp.setText(items.get(position).getToppingList());
        holder.spImage.setImageResource(items.get(position).getImage());
    }


    /**
     * Getter for the number of items in the ArrayList
     * @return int number of items in the list
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * ItemsHolder class that populates call the row view with appropriate information
     */
    public static class ItemsHolder extends RecyclerView.ViewHolder {

        private TextView spName, spPrice, toppingsOnSp;
        private ImageView spImage;
        private ConstraintLayout parentLayout;

        /**
         * Constructor for ItemsHolder
         * @param itemView view containing the layout of each row view
         */
        public ItemsHolder(@NonNull View itemView) {
            super(itemView);
            spName = itemView.findViewById(R.id.spName);
            spPrice = itemView.findViewById(R.id.RecyclerPrice);
            toppingsOnSp = itemView.findViewById(R.id.toppingsOnSp);
            spImage = itemView.findViewById(R.id.spImage);
            parentLayout = itemView.findViewById(R.id.rowLayout);

            /**
             * Initializes the views within the itemView and sets an onClickListener for the row layout
             *  Clicking on a row will navigate to SpecialitySelectedActivity
             */
            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), SpecialitySelectedActivity.class);
                    intent.putExtra("TYPE", spName.getText());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
