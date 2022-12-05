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
    private ListView ordersListView;
    private Button backBtn;
    private TextView noOrders;
    private ArrayList<Order> chefOrders;
    private ChefOrderAdapter chefOrderAdapter;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_status_orders);
        initializeVariables();
    }

    private void initializeVariables() {
        ordersListView = findViewById(R.id.chefOrderListView);
        ordersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showOrderDialog(chefOrders.get(position));
            }
        });

        backBtn = findViewById(R.id.backToChefHome);
        backBtn.setOnClickListener(this);

        chefOrders = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        chefID = extras.getString("chefID");

        reference = FirebaseDatabase.getInstance().getReference("Users").child(chefID).child("orders");

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
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (!ds.getKey().equals("0")) {
                        Order order = ds.getValue(Order.class);
                        chefOrders.add(order);
                    }
                }
                ordersListView.setAdapter(chefOrderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showOrderDialog(Order order) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_order_status_dialog, null);
        dialogBuilder.setView(dialogView);

        final Spinner orderStatusOptions = (Spinner) dialogView.findViewById(R.id.orderStatusSpinner);
        final Button buttonConfirm = (Button) dialogView.findViewById(R.id.confirmStatusModifications);
        final Button buttonBack = (Button) dialogView.findViewById(R.id.backToOrdersChef);

        String[] orderStatus = {"Pending Chef Approval", "Order being prepared", "Order on the way", "Order Delivered","Order Rejected"};

        // Creating adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, orderStatus);
        orderStatusOptions.setAdapter(adapter);

        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String orderQuantity = mealQuantity.getText().toString();
//                if (orderQuantity.isEmpty() || orderQuantity.equals("0")) {
//                    mealQuantity.setError("Must Specify Quantity! (Cannot be 0)");
//                    mealQuantity.requestFocus();
//                } else {
////                    updateOrderStatus(Order order);
//                    b.dismiss();
////                    createAlertDialog(nameChef);
//                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }

    public void updateOrderStatus(Order order) {

    }
}