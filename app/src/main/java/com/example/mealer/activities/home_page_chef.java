package com.example.mealer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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

    //Instance Variables***********************************************
    private double length;
    private Button logout, menu;
    private TextView welcome, editMenu;
    private String welcomeMessage, suspensionMessage, chefID, suspended, suspensionLength;
    private double activatedAt = Double.MAX_VALUE;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_chef);

        logout = findViewById(R.id.btn_Logout_Chef);
        logout.setOnClickListener(this);
        menu = findViewById(R.id.btnUpdateMenu);
        menu.setOnClickListener(this);

        welcome = (TextView) findViewById(R.id.textViewChefWelcome);

        Bundle extras = getIntent().getExtras();

        welcome = findViewById(R.id.textViewChefWelcome);
        welcome.setText(welcomeMessage);
        editMenu = findViewById(R.id.textViewEditMenu);

        chefID = extras.getString("userID");
        suspended = extras.getString("suspended");
        suspensionLength = extras.getString("suspensionLength");

        if (suspended.equals("true")) {
            suspensionMessage = extras.getString("suspension");
            welcome.setText(suspensionMessage);
            menu.setVisibility(View.INVISIBLE);
            editMenu.setVisibility(View.VISIBLE);
        } else {
            welcomeMessage = extras.getString("welcomeChef");
            welcome.setText(welcomeMessage);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Logout_Chef:
                startActivity(new Intent(this, homePage.class));
                break;
            case R.id.btnUpdateMenu:
                startActivity(new Intent(this, ChefMenu.class));
        }
    }
}