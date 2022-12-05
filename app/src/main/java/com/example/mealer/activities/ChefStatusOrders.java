package com.example.mealer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealer.R;
import com.example.mealer.adapters.ChefOrderAdapter;
import com.example.mealer.adapters.ClientOrderAdapter;
import com.example.mealer.structure.Chef;
import com.example.mealer.structure.Meal;
import com.example.mealer.structure.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChefStatusOrders extends AppCompatActivity implements View.OnClickListener {
    private String chefID, clientOrderID;
    private String newStatus;
    private ListView ordersListView;
    private Button backBtn, removeRejectedOrders;
    private TextView noNewOrders;
    private ArrayList<Order> chefOrders;
    private ArrayList<String> chefOrdersID;
    private ChefOrderAdapter chefOrderAdapter;
    private DatabaseReference reference, orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_status_orders);
        initializeVariables();
    }

    private void initializeVariables() {
        // List View
        ordersListView = findViewById(R.id.chefOrderListView);
        ordersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = chefOrders.get(position);
                if (order.getStatus().equals("Order Delivered") || order.getStatus().equals("Order Rejected")) {
                    Toast.makeText(getApplicationContext(), "Cannot change the status of a rejected or delivered meal!", Toast.LENGTH_LONG).show();
                } else {
                    showOrderDialog(order, chefOrdersID.get(position));
                }
            }
        });

        // Back Button
        backBtn = findViewById(R.id.backToChefHome);
        backBtn.setOnClickListener(this);

        removeRejectedOrders = findViewById(R.id.removeRejectedOrders);
        removeRejectedOrders.setOnClickListener(this);

        chefOrders = new ArrayList<>();
        chefOrdersID = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        chefID = extras.getString("chefID");

        reference = FirebaseDatabase.getInstance().getReference("Users");
        orderRef = FirebaseDatabase.getInstance().getReference("Users").child(chefID).child("orders");

        chefOrderAdapter = new ChefOrderAdapter(ChefStatusOrders.this, chefOrders);

        noNewOrders = findViewById(R.id.textView10);
        ordersListView.setEmptyView(noNewOrders);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToChefHome:
                finish();
                break;
            case R.id.removeRejectedOrders:
                removeRejectedOrders();
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chefOrders.clear();
                chefOrdersID.clear();
                DataSnapshot dataSnapshot = snapshot.child(chefID).child("orders");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (!ds.getKey().equals("0")) {
                        Order order = ds.getValue(Order.class);
                        chefOrders.add(order);
                        chefOrdersID.add(ds.getKey());
                    }
                }
                ordersListView.setAdapter(chefOrderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showOrderDialog(Order order, String orderID) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_order_status_dialog, null);
        dialogBuilder.setView(dialogView);

        final Spinner orderStatusOptions = (Spinner) dialogView.findViewById(R.id.orderStatusSpinner);
        final EditText eta = (EditText) dialogView.findViewById(R.id.editTextETA);
        final Button buttonConfirm = (Button) dialogView.findViewById(R.id.confirmStatusModifications);
        final Button buttonBack = (Button) dialogView.findViewById(R.id.backToOrdersChef);

        String[] orderStatus = {"Order being prepared", "Order on the way", "Order Delivered", "Order Rejected"};

        // Creating adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, orderStatus);
        orderStatusOptions.setAdapter(adapter);

        final AlertDialog b = dialogBuilder.create();
        b.show();

        orderStatusOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newStatus = orderStatus[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newETA = eta.getText().toString();
                updateOrderStatus(order, orderID, newStatus, newETA);
                eta.setText("");
                b.dismiss();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }

    public void updateOrderStatus(Order order, String chefOrderID, String newStatus, String newETA) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String clientID = order.getClientID();
                DataSnapshot chefSnap = snapshot.child(chefID).child("orders").child(chefOrderID);
                DataSnapshot orderClientSnap = snapshot.child(clientID).child("orders");

                for (DataSnapshot ds : orderClientSnap.getChildren()) {
                    Order clientOrder = ds.getValue(Order.class);

                    if (order.getChefID().equals(clientOrder.getChefID()) && order.getClientID().equals(clientOrder.getClientID())
                    && order.getDeliveryTime().equals(clientOrder.getDeliveryTime()) && order.getQuantity().equals(clientOrder.getQuantity())
                    && order.getStatus().equals(clientOrder.getStatus()) && order.getMeal().getMealName().equals(clientOrder.getMeal().getMealName())
                    && order.getMeal().getMealDescription().equals(clientOrder.getMeal().getMealDescription())
                    && order.getMeal().getMealPrice().equals(clientOrder.getMeal().getMealPrice())
                    && order.getMeal().getRating().equals(clientOrder.getMeal().getRating())
                    && order.getMeal().getVegetarian().equals(clientOrder.getMeal().getVegetarian())) { // if order chef object == order client object, we found the order
                        clientOrderID = ds.getKey();
                        System.out.println("Client Order ID is: " + clientOrderID);
                        break;
                    }
                }
                System.out.println("Client Order ID is: " + clientOrderID);
                DataSnapshot clientSnap = snapshot.child(clientID).child("orders").child(clientOrderID);

                // Set new Status of the order for the client and the chef
                clientSnap.getRef().child("status").setValue(newStatus);
                chefSnap.getRef().child("status").setValue(newStatus);

                // Set new delivery time for the client and the chef
                if (!newETA.isEmpty()) {
                    clientSnap.getRef().child("deliveryTime").setValue(newETA);
                    chefSnap.getRef().child("deliveryTime").setValue(newETA);
                }
                Toast.makeText(getApplicationContext(), "Order Updated Successfully", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void removeRejectedOrders() {
        orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Order order = ds.getValue(Order.class);
                    if (order.getStatus().equals("Order Rejected") || order.getStatus().equals("Order Delivered")) {
                        String orderToDelete = ds.getKey();
                        orderRef.child(orderToDelete).removeValue();
                    }
                }
                Toast.makeText(getApplicationContext(), "Removed Completed Orders", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}