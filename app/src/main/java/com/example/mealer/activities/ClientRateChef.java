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
import android.widget.Toast;

import com.example.mealer.R;
import com.example.mealer.adapters.RateMealAdapter;
import com.example.mealer.structure.Meal;
import com.example.mealer.structure.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ClientRateChef extends AppCompatActivity implements View.OnClickListener {
    private ListView pastOrdersListView;
    private TextView noOrders;
    private Button back;
    private String clientID, chefMealID;
    private DatabaseReference reference, complaintRef, chefRef;
    private ArrayList<Order> pastOrdersList;
    private ArrayList<String> pastOrdersID;
    private RateMealAdapter rateMealAdapter;
    private Meal meal;

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
                showRatingDialog(pastOrdersList.get(position), pastOrdersID.get(position));
            }
        });

        back = findViewById(R.id.backToClientHomePage);
        back.setOnClickListener(this);

        reference = FirebaseDatabase.getInstance().getReference("Users").child(clientID).child("orders");
        complaintRef = FirebaseDatabase.getInstance().getReference("Complaints");

        pastOrdersList = new ArrayList<>();
        pastOrdersID = new ArrayList<>();

        rateMealAdapter = new RateMealAdapter(ClientRateChef.this, pastOrdersList);

        noOrders = findViewById(R.id.txtViewNoOrdersToRate);
        pastOrdersListView.setEmptyView(noOrders);
    }

    @Override
    protected void onStart() {
        super.onStart();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pastOrdersList.clear();
                pastOrdersID.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Order order = ds.getValue(Order.class);
                    String id = ds.getKey();
                    if (!ds.getKey().equals("0") && order.getStatus().equals("Order Delivered")) {
                        pastOrdersList.add(order);
                        pastOrdersID.add(id);
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

    public void showRatingDialog(Order order, String orderID) {

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
                } if (Double.parseDouble(newRating) > 5 || Double.parseDouble(newRating) < 1) {
                    rating.setError("Rating must be between 1 to 5 stars");
                    rating.requestFocus();
                } else if (!newRating.isEmpty() && !complaintToAdd) {
                    addNewRating(order, newRating, orderID);
                    b.dismiss();
                } else {
                    if (complaintTitle.isEmpty()) {
                        title.setError("Please enter a title to submit a complaint");
                        title.requestFocus();
                    } else if (complaintDescription.isEmpty()){
                        description.setError("Please enter a description to submit complaint");
                        description.requestFocus();
                    } else {
                        addNewRating(order, newRating, orderID);
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

    private void addNewRating(Order order, String newRating, String orderID) {
        String chefID = order.getChefID();
        chefRef = FirebaseDatabase.getInstance().getReference("Users").child(chefID).child("menu").child("meals");
        chefRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    meal = ds.getValue(Meal.class);
                    if (order.getMeal().getMealName().equals(meal.getMealName()) &&
                    order.getMeal().getMealDescription().equals(meal.getMealDescription())
                    && order.getMeal().getMealPrice().equals(meal.getMealPrice()) &&
                    order.getMeal().getVegetarian().equals(meal.getVegetarian())) {
                        chefMealID = ds.getKey();
                        break;

                    }
//                    else {
//                        // alert dialog saying meal has been modified or is no longer available
//                        Toast.makeText(getApplicationContext(), "Meal has been changed or is no longer available", Toast.LENGTH_LONG).show();
//                        break;
//                    }
                }

                if (meal.getRating().equals("-1")) {
                    System.out.println("New Rating: " + newRating);
                    chefRef.child(chefMealID).child("rating").setValue(newRating);
                } else {
                    Double ratingDouble = (Double.parseDouble(meal.getRating()) + Double.parseDouble(newRating))/2;
                    DecimalFormat df = new DecimalFormat("#.##");
                    ratingDouble = Double.valueOf(df.format(ratingDouble));
                    String rating = String.valueOf(ratingDouble);
                    System.out.println("New Rating: " + rating);
                    chefRef.child(chefMealID).child("rating").setValue(rating);
                }
                reference.child(orderID).removeValue();
                Toast.makeText(getApplicationContext(), "Thank you for your feedback!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}