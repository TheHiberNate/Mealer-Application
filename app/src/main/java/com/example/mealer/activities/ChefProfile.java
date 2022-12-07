package com.example.mealer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mealer.R;
import com.example.mealer.structure.Chef;
import com.example.mealer.structure.Meal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class ChefProfile extends AppCompatActivity implements View.OnClickListener{
    private Button back;
    private TextView name, email, description, soldMeals, rating;
    private Chef chef;
    private String chefID, ratingString;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_profile);

        initializeVariables();
    }

    private void initializeVariables() {
        back = findViewById(R.id.button2);
        back.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        chefID = extras.getString("chefID");

        name = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        description = findViewById(R.id.profileDescription);
        soldMeals = findViewById(R.id.profileMealsSold);
        rating = findViewById(R.id.profileRating);

        reference = FirebaseDatabase.getInstance().getReference("Users").child(chefID);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chef = snapshot.getValue(Chef.class);
                DataSnapshot mealSnap = snapshot.child("menu").child("meals");
                Double sum = 0.0;
                int count = 0;
                for (DataSnapshot ds : mealSnap.getChildren()) {
                    Meal meal = ds.getValue(Meal.class);
                    if (!meal.getRating().equals("-1")) {
                        sum += Double.parseDouble(meal.getRating());
                        count++;
                    }
                }
                if (count != 0) {
                    DecimalFormat df = new DecimalFormat("#.##");
                    ratingString = String.valueOf(Double.valueOf(df.format(sum/count)));
                    reference.child("rating").setValue(ratingString);
                }
                name.setText("Name: " + chef.getFirstName() + " " + chef.getLastName());
                email.setText("Email: " + chef.getEmail());
                description.setText("Chef Description:\n" + chef.getDescription());
                soldMeals.setText("Number of Meals Sold: " + chef.getSoldMeals());
                if (chef.getRating().equals("-1")) {
                    rating.setText("No Ratings Yet");
                } else {
                    rating.setText("Rating: " + chef.getRating() + " /5 stars");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                finish();
                break;
        }
    }
}