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
    private String chefID;
    private String newStatus;
    private ListView ordersListView;
    private Button backBtn;
    private TextView noOrders;
    private ArrayList<Order> chefOrders;
    private ArrayList<String> chefOrdersID;
    private ChefOrderAdapter chefOrderAdapter;
    private DatabaseReference reference;

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
                showOrderDialog(chefOrders.get(position), chefOrdersID.get(position));
            }
        });

        // Back Button
        backBtn = findViewById(R.id.backToChefHome);
        backBtn.setOnClickListener(this);

        chefOrders = new ArrayList<>();
        chefOrdersID = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        chefID = extras.getString("chefID");

        reference = FirebaseDatabase.getInstance().getReference("Users");

        chefOrderAdapter = new ChefOrderAdapter(ChefStatusOrders.this, chefOrders);

        noOrders = findViewById(R.id.noIncomingOrders);
        ordersListView.setEmptyView(noOrders);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToChefHome:
                finish();
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

        String[] orderStatus = {"Pending Chef Approval", "Order being prepared", "Order on the way", "Order Delivered","Order Rejected"};

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
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newETA = eta.getText().toString();
                updateOrderStatus(order, orderID, newStatus, newETA);
                eta.setText("");
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }

    public void updateOrderStatus(Order order, String orderID, String newStatus, String newETA) {
        String clientID = order.getClientID();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot clientSnap = snapshot.child(clientID).child("orders").child(orderID);
                DataSnapshot chefSnap = snapshot.child(chefID).child("orders").child(orderID);

                // Set new Status of the order for the client and the chef
                clientSnap.getRef().child("status").setValue(newStatus);
                chefSnap.getRef().child("status").setValue(newStatus);

                // Set new delivery time for the client and the chef
                if (!newETA.isEmpty()) {
                    clientSnap.getRef().child("deliveryTime").setValue(newETA);
                    chefSnap.getRef().child("deliveryTime").setValue(newETA);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}