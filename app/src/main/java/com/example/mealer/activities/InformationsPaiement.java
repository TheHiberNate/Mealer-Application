package com.example.mealer.activities;

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

public class InformationsPaiement extends AppCompatActivity implements View.OnClickListener {
    private EditText textpayment;
    private EditText expirationdate;
    private EditText codeCVCC;
    private Button register;
    private FirebaseAuth mAuth;

    private void initializeVariables(){

        register = (Button) findViewById(R.id.registerclient);
        register.setOnClickListener(this);
        textpayment = (EditText) findViewById(R.id.textpayment);
        expirationdate = (EditText) findViewById(R.id.expirationdate);
        codeCVCC = (EditText) findViewById(R.id.codeCVCC);

    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.registerclient:
                startActivity(new Intent(this, home_page_client.class));
                break;
        }
    }
    public String getPayment(){
        return textpayment.getText().toString().trim();
    }
    private User createUser(String role, String firstName, String lastName, String email, String address, String payment, String description) {
        if (role.equals("Client")) {
            return new Client(firstName, lastName, email, address, payment);
        } else {
            return new Chef(firstName, lastName, email, address, payment, description);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations_paiement);
    }
    private boolean validCredentials(){
        boolean isValid = true;
        String payment = textpayment.getText().toString().trim();
        String expdate = expirationdate.getText().toString().trim();
        String code = codeCVCC.getText().toString().trim();

        if (payment.isEmpty()) {
            textpayment.setError("Please Enter Payment Information");
            textpayment.requestFocus();
            isValid = false;
        } else if ( payment.length()!=16){
            textpayment.setError("Please enter valid card");
            textpayment.requestFocus();
            isValid = false;
        }if (expdate.length()!=4){
            textpayment.setError("Please Enter valid date");
            textpayment.requestFocus();
            isValid = false;
        }
        if (code.length()!=3) {
            textpayment.setError("Please Enter valid code");
            textpayment.requestFocus();
            isValid = false;
        }

        return isValid;
    }
    public boolean getValidCredentials(){
        return validCredentials();
    }

    private void registerUser(){
        register_page tempinfo = new register_page();
        final String firstName = tempinfo.getFirstName();
        final String lastName = tempinfo.getLastName();
        final String email = tempinfo.getEmail();
        final String password = tempinfo.getPassword();
        final String address = tempinfo.getAdress();
        final String paymentclient = getPayment();
        final String description = tempinfo.getDescription();
        final String role = tempinfo.getRole();

        if (validCredentials()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                final User user = createUser(role, firstName, lastName, email, address, paymentclient, description);
//                                User user = new User(firstName, lastName, email, address,"Chef");

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(mAuth.getCurrentUser().getUid()).setValue(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(InformationsPaiement.this, "Registered Successfully! Please Login to get Started!", Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(InformationsPaiement.this, homePage.class));
                                                }
                                                else {
                                                    Toast.makeText(InformationsPaiement.this, "Registration Failed, Try again later", Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(InformationsPaiement.this, homePage.class));
                                                }
                                            }
                                        });
                            }
                            else {
                                Toast.makeText(InformationsPaiement.this, "Registration Failed, Try again later", Toast.LENGTH_LONG).show();                                                }
                        }
                    });
        }



    }


}