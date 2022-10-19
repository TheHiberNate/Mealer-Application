package com.example.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class home_page_admin extends AppCompatActivity implements View.OnClickListener {
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_admin);

        logout = (Button) findViewById(R.id.btnLogoutAdmin);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogoutAdmin:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}