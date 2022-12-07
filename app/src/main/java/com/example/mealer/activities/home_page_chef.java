package com.example.mealer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mealer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class home_page_chef extends AppCompatActivity implements View.OnClickListener {

    //Instance Variables***********************************************
    private Button logout, menu, orderStatus;
    private ImageView profile;
    private TextView welcome, introMsg;
    private CardView menuCardView;
    private String welcomeMessage, suspensionMessage, chefID, suspensionLength;
    private Boolean suspended;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_chef);

        initializeVariables();
    }

    private void initializeVariables() {
        logout = findViewById(R.id.btn_Logout_Chef);
        logout.setOnClickListener(this);
        menu = findViewById(R.id.btnUpdateMenu);
        menu.setOnClickListener(this);
        orderStatus = findViewById(R.id.btnOrders);
        orderStatus.setOnClickListener(this);

        welcome = (TextView) findViewById(R.id.textViewChefWelcome);

        Bundle extras = getIntent().getExtras();

        welcome = findViewById(R.id.textViewChefWelcome);
        welcome.setText(welcomeMessage);

        introMsg = findViewById(R.id.introMesg);
        menuCardView = (CardView) findViewById(R.id.sec);

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(this);

        chefID = extras.getString("userID");
        suspended = Boolean.valueOf(extras.getString("suspended"));
        suspensionLength = extras.getString("suspensionLength");

        if (suspended) {
            suspensionMessage = extras.getString("suspension");
            welcome.setText(suspensionMessage);
            menuCardView.setVisibility(View.INVISIBLE);
            introMsg.setVisibility(View.INVISIBLE);
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
                Intent intent = new Intent(this, ChefMenu.class);
                intent.putExtra("chefID", chefID);
                startActivity(intent);
                break;
            case R.id.btnOrders:
                Intent newIntent = new Intent(this, ChefStatusOrders.class);
                newIntent.putExtra("chefID", chefID);
                startActivity(newIntent);
                break;
            case R.id.profile:
                Intent profileIntent = new Intent(this, ChefProfile.class);
                profileIntent.putExtra("chefID", chefID);
                startActivity(profileIntent);
                break;
        }
    }
}