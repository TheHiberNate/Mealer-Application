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

public class ClientOrderAdapter extends ArrayAdapter<Order> {
    ArrayList<Order> orders;
    private Activity context;
    private String chefName;
    private DatabaseReference chefRef;

    public ClientOrderAdapter(Activity context, ArrayList<Order> orders) {
        super(context, R.layout.client_order_adapter, orders);
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.client_order_adapter, null, true);

        TextView orderMealName = listViewItem.findViewById(R.id.orderMealName);
        TextView orderChefName = listViewItem.findViewById(R.id.orderName);
        TextView orderQuantity = listViewItem.findViewById(R.id.orderQuantity);
        TextView orderDeliveryTime = listViewItem.findViewById(R.id.orderDeliveryTimeChef);
        TextView orderStatus = listViewItem.findViewById(R.id.orderStatusChef);

        Order order = orders.get(position);
        System.out.println(order.getMeal().getMealName());
        orderMealName.setText(order.getMeal().getMealName());
        orderDeliveryTime.setText("Estimated Delivery Time: " + order.getDeliveryTime());
        orderQuantity.setText("Quantity: " + order.getQuantity());
        orderStatus.setText("Order Status: " + order.getStatus());

        chefRef = FirebaseDatabase.getInstance().getReference("Users").child(order.getChefID());
        chefRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String chefFirstName = snapshot.child("firstName").getValue().toString();
                String chefLastName = snapshot.child("lastName").getValue().toString();
                chefName = chefFirstName + " " + chefLastName;
                orderChefName.setText(chefName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return listViewItem;
    }
}
