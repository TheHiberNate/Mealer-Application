package com.example.mealer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register_page extends AppCompatActivity implements View.OnClickListener {

    private TextView homePage;
    private EditText editTextName, editTextLastName, editTextPassword, editTextEmail;
    private Button register;
    private RadioButton client, chef;
//    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        mAuth = FirebaseAuth.getInstance();

        register = (Button) findViewById(R.id.btn_Register2);
        register.setOnClickListener(this);

        homePage = (TextView) findViewById(R.id.textBacktoHome);
        homePage.setOnClickListener(this);

        client = (RadioButton) findViewById(R.id.radioBtnClient);
        chef = (RadioButton) findViewById(R.id.radioBtnChef);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
    }

    @Override
    public void onClick(View v) {
//        boolean checked = ((RadioButton) view).isChecked();
        switch (v.getId()) {
            case R.id.textBacktoHome:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btn_Register2:
                registerUser();
                break;
//            case R.id.radioBtnClient:
//                if (checked) {role="Client";}
//                break;
//            case R.id.radioBtnChef:
//                if (checked) {role="Chef";}
//                break;
        }
    }

    private void registerUser() {
        String firstName = editTextName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        boolean checked = chef.isChecked();

        //Checks if corresponding fields are empty
        if (firstName.isEmpty()) {
            editTextName.setError("Please Enter your First Name");
            editTextName.requestFocus();
            return;
        }
        if (lastName.isEmpty()) {
            editTextLastName.setError("Please Enter your Last Name");
            editTextLastName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Please Enter your Email");
            editTextEmail.requestFocus();
            return;
        }
        // Invalid email address
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please Enter a Valid Email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Please Enter your Password");
            editTextPassword.requestFocus();
            return;
        }
        // Not long enough password (Minimum 6 characters)
        if (password.length() < 6) {
            editTextPassword.setError("Password must be at least 6 characters!");
            editTextPassword.requestFocus();
            return;
        }
        if (checked != true) { // chef radio button is no checked
            role = "Client";
        }
        else {
            role = "Chef";
        }

        // set progress bar to visible
//        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(lastName, firstName, email, password);

//                    if (role == "Client") {
//                        user = new Client(lastName, firstName, email, password);
//                    }
//                    else {
//                        user = new Chef(lastName, firstName, email, password);
//                    }
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(register_page.this, "Registered Successfully!", Toast.LENGTH_LONG).show();
//                                        progressBar.setVisibility(View.INVISIBLE);
                                        // redirect to Login page
                                    }
                                    else {
                                        Toast.makeText(register_page.this, "Registration Failed, Try again!", Toast.LENGTH_LONG).show();
//                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                } else {
                    Toast.makeText(register_page.this, "Registration Failed, Try again!", Toast.LENGTH_LONG).show();
//                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
//        mAuth.createUserWithEmailAndPassword(email, password);
    }

}