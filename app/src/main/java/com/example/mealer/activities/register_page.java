package com.example.mealer.activities;


import android.content.Intent;
import android.os.Bundle;
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
            editTextEmail, editTextAddress , editTextDescription;
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
        register = findViewById(R.id.btn_Continue);
        register.setOnClickListener(this);
        btnhomePage = findViewById(R.id.btnBackHome);
        btnhomePage.setOnClickListener(this);
        // EditText
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextAddress = findViewById(R.id.editTextAddress);

        editTextDescription = findViewById(R.id.editTextDescription);
        // RadioGroup
        usersRadioGroup = findViewById(R.id.radioGroupUsers);
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
    public  String getFirstName(){
        return  editTextName.getText().toString().trim();

    }public  String getLastName(){
        return editTextLastName.getText().toString().trim();

    }public  String getEmail(){
         return editTextEmail.getText().toString().trim();

    } public String getPassword(){
        return editTextPassword.getText().toString().trim();
    }
    public String getAdress(){
        return  editTextAddress.getText().toString().trim();
    }
    public String getDescription(){
        return editTextDescription.getText().toString().trim();
    }
    public String getRole(){
        int radioBtnInt = usersRadioGroup.getCheckedRadioButtonId();
        userRadioButton = findViewById(radioBtnInt);
         String role = userRadioButton.getText().toString();
         return role;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackHome:
                startActivity(new Intent(this, homePage.class));
                break;
            case R.id.btn_Continue:
                if (validCredentials()) {
                    if (chefIsChecked==false) {
                        Intent intent = new Intent(this,InformationsPaiement.class);
                        intent.putExtra("firstname",getFirstName());
                        intent.putExtra("lastname",getLastName());
                        intent.putExtra("email",getEmail());
                        intent.putExtra("adress",getAdress());
                        intent.putExtra("password",getPassword());

                        startActivity(intent);

                        break;
                    }
                    else {
                        Intent intent = new Intent(this,Informationspaiementchef.class);
                        intent.putExtra("firstname",getFirstName());
                        intent.putExtra("lastname",getLastName());
                        intent.putExtra("email",getEmail());
                        intent.putExtra("adress",getAdress());
                        intent.putExtra("description",getDescription());
                        intent.putExtra("password",getPassword());
                        startActivity(intent);
                        System.out.println(getFirstName() + " " + getLastName());
                        break;
                    }
                }



        }
    }


    private boolean validCredentials() {
        boolean isValid = true;

        String firstName = editTextName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
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

}