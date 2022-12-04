package com.example.mealer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mealer.R;
import com.example.mealer.adapters.ClientOrderAdapter;
import com.example.mealer.structure.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClientStatusOrders extends AppCompatActivity implements View.OnClickListener{
    private String clientID;
    private ListView ordersListView;
    private Button backBtn;
    private TextView noOrders;
    private ArrayList<Order> clientOrders;
    private ClientOrderAdapter clientOrderAdapter;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_status_orders);

        initializeVariables();
    }

    private void initializeVariables() {
        ordersListView = findViewById(R.id.clientOrderListView);
        ordersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        backBtn = findViewById(R.id.backClientHomeBtn);
        backBtn.setOnClickListener(this);

        clientOrders = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        clientID = extras.getString("clientID");

        reference = FirebaseDatabase.getInstance().getReference("Users").child(clientID).child("orders");

        clientOrderAdapter = new ClientOrderAdapter(ClientStatusOrders.this, clientOrders);

        noOrders = findViewById(R.id.noOrderTxtView);
        ordersListView.setEmptyView(noOrders);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backClientHomeBtn:
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
                clientOrders.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (!ds.getKey().equals("0")) {
                        Order order = ds.getValue(Order.class);
                        clientOrders.add(order);
                    }
                }
                ordersListView.setAdapter(clientOrderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}