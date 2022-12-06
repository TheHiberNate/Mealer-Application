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
import android.widget.TextView;

import com.example.mealer.R;
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
    private DatabaseReference reference, complaintRef;
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
        complaintRef = FirebaseDatabase.getInstance().getReference("Complaints");

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
        final View dialogView = inflater.inflate(R.layout.rate_meal_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText rating = dialogView.findViewById(R.id.editTextNewRating);
        final EditText title = dialogView.findViewById(R.id.editTextTitleComplaint);
        final EditText description = dialogView.findViewById(R.id.editTextDescriptionComplaint);
        final Button btnConfirm = dialogView.findViewById(R.id.confirmButton);
        final Button btnBack = dialogView.findViewById(R.id.backButton);

        final AlertDialog b = dialogBuilder.create();
        b.show();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newRating = rating.getText().toString();
                String complaintTitle = title.getText().toString();
                String complaintDescription = description.getText().toString();
                // Check if one of the complaint fields has text
                Boolean complaintToAdd = !complaintTitle.isEmpty() || !complaintDescription.isEmpty();

                if (newRating.isEmpty()) {
                    rating.setError("Please enter a rating");
                    rating.requestFocus();
                } else if (!newRating.isEmpty() && !complaintToAdd) {
                    addNewRating(order, newRating);
                    b.dismiss();
                } else {
                    if (complaintTitle.isEmpty()) {
                        title.setError("Please enter a title to submit a complaint");
                        title.requestFocus();
                    } else if (complaintDescription.isEmpty()){
                        description.setError("Please enter a description to submit complaint");
                        description.requestFocus();
                    } else {
                        addNewRating(order, newRating);
                        addNewComplaint(order, complaintTitle, complaintDescription);
                        b.dismiss();
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }

    private void addNewComplaint(Order order, String title, String description) {
        String id = complaintRef.push().getKey();
        complaintRef.child(id).child("chefID").setValue(order.getChefID());
        complaintRef.child(id).child("clientID").setValue(order.getClientID());
        complaintRef.child(id).child("title").setValue(title);
        complaintRef.child(id).child("description").setValue(description);
    }

    private void addNewRating(Order order, String newRating) {

    }
}