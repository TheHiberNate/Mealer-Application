package com.example.mealer;


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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register;
    private EditText email, password;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVariables();
    }

    public void initializeVariables() {
        register = (Button) findViewById(R.id.btn_Register);
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.btn_Login);
        signIn.setOnClickListener(this);

        email = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        password = (EditText) findViewById(R.id.editTextTextPassword2);

        mAuth = FirebaseAuth.getInstance();
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
            mAuth.signInWithEmailAndPassword(email_check, password_check).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        // if validadmin (check email and password for admin) then
//                        startActivity(new Intent(MainActivity.this, home_page_admin.class));

                        //redirect to client profile if user is client
                        //else if (user is client)
                        startActivity(new Intent(MainActivity.this, home_page_client.class));

                        //redirect to chef profile if user is chef
//                        startActivity(new Intent(MainActivity.this, home_page_chef.class));

                    }
                    else{
                        Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                    }
                }
            });
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