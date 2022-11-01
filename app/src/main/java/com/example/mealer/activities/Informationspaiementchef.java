package com.example.mealer.activities;

import static com.example.mealer.R.id.registerchef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mealer.R;
import com.example.mealer.structure.Chef;
import com.example.mealer.structure.Client;
import com.example.mealer.structure.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Informationspaiementchef extends AppCompatActivity implements View.OnClickListener{
    private EditText accountnumber,succursalenumber,banknumber;
    private Button registerchef;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informationspaiementchef);
        initializeVariables();
    }

    private void initializeVariables(){
        accountnumber = (EditText) findViewById(R.id.accountnumber);
        succursalenumber = (EditText) findViewById(R.id.succursalenumber);
        banknumber = (EditText) findViewById(R.id.banknumber);
        registerchef = (Button) findViewById(R.id.registerchef);
        registerchef.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.registerchef:
                registerUser();
                break;
        }
    }

    private boolean validCredentials(){
        boolean isValid=true;
        String account = accountnumber.getText().toString().trim();
        String bank = banknumber.getText().toString().trim();
        String succursale = succursalenumber.getText().toString().trim();
        if (account.isEmpty() || account.length()!=7){
            accountnumber.setError("Please Enter valid accountnumber");
            accountnumber.requestFocus();
            isValid = false;
        } if (bank.isEmpty() || bank.length()!=3){
            banknumber.setError("Please Enter valid bankmuber");
            isValid = false;
        } if (succursale.isEmpty() || succursale.length()!=5){
            succursalenumber.setError("Please Enter valid succursalenumber");
            isValid = false;
        }
        return isValid;
    }
    public boolean getValidCredentials(){
        return validCredentials();
    }
    public String getPayment(){
        return accountnumber.getText().toString().trim();
    }
    private User createUser(String role, String firstName, String lastName, String email, String address, String payment, String description) {
        if (role.equals("Client")) {
            return new Client(firstName, lastName, email, address, payment);
        } else {
            return new Chef(firstName, lastName, email, address, payment, description);
        }
    }

    private void registerUser(){
        register_page tempinfo = new register_page();
        final String firstName = tempinfo.getFirstName();
        final String lastName = tempinfo.getLastName();
        final String email = tempinfo.getEmail();
        final String password = tempinfo.getPassword();
        final String address = tempinfo.getAdress();
        final String paymentchef = getPayment();
        final String description = tempinfo.getDescription();
        final String role = tempinfo.getRole();

        if (validCredentials()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                final User user = createUser(role, firstName, lastName, email, address, paymentchef, description);
//                                User user = new User(firstName, lastName, email, address,"Chef");

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(mAuth.getCurrentUser().getUid()).setValue(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(Informationspaiementchef.this, "Registered Successfully! Please Login to get Started!", Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(Informationspaiementchef.this, homePage.class));
                                                }
                                                else {
                                                    Toast.makeText(Informationspaiementchef.this, "Registration Failed, Try again later", Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(Informationspaiementchef.this, homePage.class));
                                                }
                                            }
                                        });
                            }
                            else {
                                Toast.makeText(Informationspaiementchef.this, "Registration Failed, Try again later", Toast.LENGTH_LONG).show();                                                }
                        }
                    });
        }



    }







}