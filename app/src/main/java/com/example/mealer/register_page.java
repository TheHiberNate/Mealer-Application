package com.example.mealer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class register_page extends AppCompatActivity implements View.OnClickListener {

    private TextView HomePage;
    private EditText editTextUsername, editTextPassword, editTextEmail;
    private Button register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        mAuth = FirebaseAuth.getInstance();

        register = (Button) findViewById(R.id.btn_Register2);
        register.setOnClickListener(this);

        //HomePage = (TextView) findViewById(R.id.textBacktoHome);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Register2:
                registerUser();
        }
    }

    private void registerUser() {
    }
}