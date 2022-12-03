package com.example.mealer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mealer.R;

public class home_page_client extends AppCompatActivity implements View.OnClickListener {
    private Button logout, order;
    private TextView welcome;
    private String welcomeMessage, clientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_client);

        logout = findViewById(R.id.btn_Logout_Client);
        logout.setOnClickListener(this);

        order = findViewById(R.id.orderbutton);
        order.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        welcomeMessage = extras.getString("welcomeClient");

        welcome = findViewById(R.id.textViewWelcomeClient);
        welcome.setText(welcomeMessage);
        clientID = extras.getString("userID");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Logout_Client:
                startActivity(new Intent(this, homePage.class));
                break;
            case R.id.orderbutton:
                Intent intent = new Intent(this, ClientSearchMeal.class);
                intent.putExtra("clientID", clientID);
                startActivity(intent);
        }
    }
}