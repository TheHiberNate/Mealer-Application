package com.example.mealer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mealer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class home_page_chef extends AppCompatActivity implements View.OnClickListener {
    //Class Variables**************************************************
    private static final double DAY = 8.64e+7;
    private static final double WEEK = 6.048e+8;
    private static final double MONTH = 2.592e+9;
    private static final double YEAR = 3.154e+10;

    //Instance Variables***********************************************
    private Button logout;
    private TextView welcome;
    private String welcomeMessage, suspensionMessage, chefID, isSuspended;
    private double activatedAt = Double.MAX_VALUE;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_chef);

        logout = (Button) findViewById(R.id.btn_Logout_Chef);
        logout.setOnClickListener(this);

        welcome = (TextView) findViewById(R.id.textViewChefWelcome);

        Bundle extras = getIntent().getExtras();

        chefID = extras.getString("userID");
        isSuspended = extras.getString("isSuspended");
        suspensionMessage = extras.getString("suspension");
        welcomeMessage = extras.getString("welcomeChef");

        if (isSuspended.equals("true")) {
            welcome.setText(suspensionMessage);
        } else {
            welcome.setText(welcomeMessage);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Logout_Chef:
                startActivity(new Intent(this, homePage.class));
                break;
        }
    }
//
//    public void activateTimer() { activatedAt = System.currentTimeMillis(); }
//
//    public boolean isActive() {
//        double activeFor = System.currentTimeMillis() - activatedAt;
//        if (suspensionLength == "1year") {
//            return activeFor >=0 && activeFor <= YEAR;
//        } else if (suspensionLength == "1month") {
//            return activeFor >= 0 && activeFor <= MONTH;
//        } else if (suspensionLength == "1week") {
//            return activeFor >= 0 && activeFor <= WEEK;
//        } else {
//            return activeFor >= 0 && activeFor <= DAY;
//        }
//    }

}