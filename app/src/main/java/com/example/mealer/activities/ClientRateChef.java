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
import com.example.mealer.adapters.ClientOrderAdapter;
import com.example.mealer.adapters.RateMealAdapter;
import com.example.mealer.structure.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClientRateChef extends AppCompatActivity implements View.OnClickListener {
    private ListView pastOrdersListView;
    private TextView noOrders;
    private Button back;
    private String clientID;
    private DatabaseReference reference;
    private ArrayList<Order> pastOrdersList;
    private RateMealAdapter rateMealAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_rate_chef);

        initializeVariables();
    }

    private void initializeVariables() {
        Bundle extras = getIntent().getExtras();
        clientID = extras.getString("clientID");

        pastOrdersListView = findViewById(R.id.pastOrders);
        pastOrdersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showRatingDialog(pastOrdersList.get(position));
            }
        });

        back = findViewById(R.id.backToClientHomePage);
        back.setOnClickListener(this);

        reference = FirebaseDatabase.getInstance().getReference("Users").child(clientID).child("orders");

        pastOrdersList = new ArrayList<>();

        rateMealAdapter = new RateMealAdapter(ClientRateChef.this, pastOrdersList);

        noOrders = findViewById(R.id.txtViewNoOrders);
        pastOrdersListView.setEmptyView(noOrders);
    }

    @Override
    protected void onStart() {
        super.onStart();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pastOrdersList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Order order = ds.getValue(Order.class);
                    if (!ds.getKey().equals("0") && order.getStatus().equals("Order Delivered")) {
                        pastOrdersList.add(order);
                    }
                }
                pastOrdersListView.setAdapter(rateMealAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToClientHomePage:
                finish();
                break;
        }
    }

    public void showRatingDialog(Order order) {

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orderStatus);
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
}