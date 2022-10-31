package com.example.mealer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mealer.R;

public class home_page_client extends AppCompatActivity implements View.OnClickListener {
    private Button logout;
    private TextView welcome;
    private String welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_client);

        logout = (Button) findViewById(R.id.btn_Logout_Client);
        logout.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        welcomeMessage = extras.getString("welcomeClient");

        welcome = (TextView) findViewById(R.id.textViewWelcomeClient);
        welcome.setText(welcomeMessage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Logout_Client:
                startActivity(new Intent(this, homePage.class));
                break;
        }
    }
}