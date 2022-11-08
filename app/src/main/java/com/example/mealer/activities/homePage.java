package com.example.mealer.activities;


// Test

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mealer.R;
import com.example.mealer.functions.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class homePage extends AppCompatActivity implements View.OnClickListener {

    private Button register;
    private EditText email, password;
    private Button signIn;

    private FirebaseAuth mAuth;
    private DatabaseReference referenceDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_page);
        initializeVariables();
    }

    public void initializeVariables() {
        register = findViewById(R.id.btn_Register);
        register.setOnClickListener(this);

        signIn = findViewById(R.id.btn_Login);
        signIn.setOnClickListener(this);

        email = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword2);

        mAuth = FirebaseAuth.getInstance();
        referenceDatabase = FirebaseDatabase.getInstance().getReference("Users");
    }


        @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Register:
                startActivity(new Intent(this, register_page.class));
                break;

            case R.id.btn_Login:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        final String email_check = email.getText().toString().trim();
        final String password_check = password.getText().toString().trim();

        if (validCredentials()) {
            if (Validation.validateAdmin(email_check, password_check)) {
                startActivity(new Intent(homePage.this, home_page_admin.class));
            } else {
                mAuth.signInWithEmailAndPassword(email_check, password_check).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            final String id = task.getResult().getUser().getUid();
                            referenceDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        if (dataSnapshot.getKey().equals(id)) {
                                            final String role = dataSnapshot.child("role").getValue().toString();
                                            final String firstName = dataSnapshot.child("firstName").getValue().toString();
//                                            final String lastName = dataSnapshot.child("lastName").getValue().toString();
                                            Intent intentUserWelcome;
                                            if (role.equals("Client")) {
                                                intentUserWelcome = new Intent(homePage.this, home_page_client.class);
                                                intentUserWelcome.putExtra("welcomeClient", "Welcome Customer " + firstName + "! Ready to order some Food?!");
                                            } else {
                                                intentUserWelcome = new Intent(homePage.this, home_page_chef.class);
                                                intentUserWelcome.putExtra("welcomeChef", "Welcome " + role + " " + firstName + "! Ready to make some Food?!");
                                            }
                                            startActivity(intentUserWelcome);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(homePage.this, "Database Error, Try again later", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Toast.makeText(homePage.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }

    private boolean validCredentials() {
        boolean isValid = true;

        String email_check = email.getText().toString().trim();
        String password_check = password.getText().toString().trim();

        if(email_check.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            isValid = false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email_check).matches()){
            email.setError("Please enter a valid email!");
            email.requestFocus();
            isValid = false;
        }

        if(password_check.isEmpty()){
            password.setError("Password required!");
            password.requestFocus();
            isValid = false;
        }

        if(password_check.length() < 6){
            password.setError("Password must be at least 6 characters");
            password.requestFocus();
            isValid = false;
        }

        return isValid;
    }

}