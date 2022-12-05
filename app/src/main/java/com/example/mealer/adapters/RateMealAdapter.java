package com.example.mealer.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mealer.R;
import com.example.mealer.structure.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RateMealAdapter extends ArrayAdapter<Order> {
    ArrayList<Order> orders;
    private Activity context;
    private DatabaseReference reference;

    public RateMealAdapter(Activity context, ArrayList<Order> orders) {
        super(context, R.layout.rate_meal_adapter, orders);
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.rate_meal_adapter, null, true);

        TextView mealName = listViewItem.findViewById(R.id.mealNameRateMeal);
        TextView chefName = listViewItem.findViewById(R.id.chefNameRateMeal);
        TextView quantity = listViewItem.findViewById(R.id.quantityRateMeal);
        TextView rating = listViewItem.findViewById(R.id.ratingRateMeal);

        Order order = orders.get(position);
        mealName.setText(order.getMeal().getMealName());
        quantity.setText("Quantity Ordered: " + order.getQuantity());
        if (order.getMeal().getRating().equals("-1")) {
            rating.setText("No Ratings Yet");
        } else {
            rating.setText("Current Rating: " + order.getMeal().getRating() + " /5");
        }

        reference = FirebaseDatabase.getInstance().getReference("Users").child(order.getChefID());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String chefFirstName = snapshot.child("firstName").getValue().toString();
                String chefLastName = snapshot.child("lastName").getValue().toString();
                String name = chefFirstName + " " + chefLastName;
                chefName.setText("Meal Ordered From: " + name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return listViewItem;
    }
}
