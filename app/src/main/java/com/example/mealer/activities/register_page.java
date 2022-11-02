package com.example.mealer.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mealer.functions.Validation;
import com.example.mealer.structure.Chef;
import com.example.mealer.structure.Client;
import com.example.mealer.R;
import com.example.mealer.structure.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register_page extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName, editTextLastName, editTextPassword,
            editTextEmail, editTextAddress, editTextPayment, editTextDescription;
    private Button register, btnhomePage;
    private RadioGroup usersRadioGroup;
    private RadioButton userRadioButton, radioButtonClient, radioButtonChef;
    private FirebaseAuth mAuth;
    private boolean chefIsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        initializeVariables();
    }

    private void initializeVariables() {
        // Firebase
        mAuth =  FirebaseAuth.getInstance();
        // Button
        register = (Button) findViewById(R.id.btn_Register2);
        register.setOnClickListener(this);
        btnhomePage = (Button) findViewById(R.id.btnBackHome);
        btnhomePage.setOnClickListener(this);
        // EditText
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextPayment = (EditText) findViewById(R.id.editTextPayment);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        // RadioGroup
        usersRadioGroup = (RadioGroup) findViewById(R.id.radioGroupUsers);
        usersRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               if (checkedId == R.id.radioBtnChef) {
                   editTextDescription.setVisibility(View.VISIBLE);
                   chefIsChecked = true;
               } else {
                   editTextDescription.setVisibility(View.INVISIBLE);
                   chefIsChecked = false;
               }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackHome:
                startActivity(new Intent(this, homePage.class));
                break;
            case R.id.btn_Register2:
                registerUser();
                break;
        }
    }

    private User createUser(String role, String firstName, String lastName, String email, String address, int payment, String description) {
        if (role.equals("Client")) {
            return new Client(firstName, lastName, email, address, payment);
        } else {
            return new Chef(firstName, lastName, email, address, payment, description);
        }
    }

    private boolean validCredentials() {
        boolean isValid = true;

        String firstName = editTextName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String payment = editTextPayment.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        //Checks if corresponding fields are empty and if has valid Email and Password
        if (firstName.isEmpty()) {
            editTextName.setError("Please Enter your First Name");
            editTextName.requestFocus();
            isValid = false;
        }
        if (lastName.isEmpty()) {
            editTextLastName.setError("Please Enter your Last Name");
            editTextLastName.requestFocus();
            isValid = false;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Please Enter your Email");
            editTextEmail.requestFocus();
            isValid = false;
        } else if (!Validation.validateEmail(email)) {
            editTextEmail.setError("Please Enter a Valid Email");
            editTextEmail.requestFocus();
            isValid = false;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Please Enter your Password");
            editTextPassword.requestFocus();
            isValid = false;
        } else if (password.length() < 6) { // Not long enough password (Minimum 6 characters)
            editTextPassword.setError("Password must be at least 6 characters!");
            editTextPassword.requestFocus();
            isValid = false;
        }
        if (address.isEmpty()) {
            editTextAddress.setError("Please Enter your Address");
            editTextAddress.requestFocus();
            isValid = false;
        }
        if (payment.isEmpty()) {
            editTextPayment.setError("Please Enter Payment Information");
            editTextPayment.requestFocus();
            isValid = false;
        } else if (payment.length() > 16) {
            editTextPayment.setError("Please Enter Valid Payment Method (Maxmimum of 16 digits for a pin number");
            editTextPayment.requestFocus();
            isValid = false;
        }
        if (description.isEmpty() && chefIsChecked) {
            editTextDescription.setError(("Please Enter a description of your yourself!"));
            editTextDescription.requestFocus();
            isValid = false;
        } else if (description.length() > 200 && chefIsChecked) {
            editTextDescription.setError(("Maximum number of characters is 100!"));
            editTextDescription.requestFocus();
            isValid = false;
        }
        return isValid;
    }

    private void registerUser() {
        final String firstName = editTextName.getText().toString().trim();
        final String lastName = editTextLastName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String address = editTextAddress.getText().toString().trim();
        final String payment = editTextPayment.getText().toString().trim();
        final String description = editTextDescription.getText().toString().trim();

        int radioBtnInt = usersRadioGroup.getCheckedRadioButtonId();
        userRadioButton = findViewById(radioBtnInt);
        final String role = userRadioButton.getText().toString();

        if (validCredentials()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                final User user = createUser(role, firstName, lastName, email, address, Integer.parseInt(payment), description);
//                                User user = new User(firstName, lastName, email, address,"Chef");

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(mAuth.getCurrentUser().getUid()).setValue(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(register_page.this, "Registered Successfully! Please Login to get Started!", Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(register_page.this, homePage.class));
                                                }
                                                else {
                                                    Toast.makeText(register_page.this, "Registration Failed, Try again later", Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(register_page.this, homePage.class));
                                                }
                                            }
                                        });
                            }
                            else {
                                Toast.makeText(register_page.this, "Registration Failed, Try again later", Toast.LENGTH_LONG).show();                                                }
                            }
                    });
        }
    }

}