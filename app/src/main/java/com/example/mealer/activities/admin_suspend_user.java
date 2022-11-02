package com.example.mealer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mealer.R;
import com.example.mealer.structure.Complaint;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_suspend_user extends AppCompatActivity implements View.OnClickListener {
    private TextView textViewChefName, textViewClientName;
    private Button backToComplaints, confirmSuspension, ignoreComplaint;
    private RadioGroup typeOfSuspension, durationOfSuspension;
    private Boolean specificTimeChecked;
    private String suspensionLength;
    private String chefID, clientID, chefName, clientName;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_suspend_user);

        initializeVariables();
    }

    private void initializeVariables() {

        Bundle extras = getIntent().getExtras();
        chefID = extras.getString("chefName");
        clientID = extras.getString("clientName");

        textViewChefName = (TextView) findViewById(R.id.textViewChefName);
        textViewClientName = (TextView) findViewById(R.id.textViewClientName);

        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String tempchefFirstName = snapshot.child(chefID).child("firstName").getValue().toString();
                final String tempchefLastName = snapshot.child(chefID).child("lastName").getValue().toString();
                final String tempclientFirstName = snapshot.child(clientID).child("firstName").getValue().toString();
                final String tempclientLastName = snapshot.child(clientID).child("lastName").getValue().toString();
                setChefName(tempchefFirstName + " " + tempchefLastName);
                setClientName(tempclientFirstName + " " + tempclientLastName);
                textViewClientName.setText("Complaint filed by: " + clientName);
                textViewChefName.setText("Complaint against: " + chefName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
        backToComplaints = (Button) findViewById(R.id.btn_backToComplaints);
        backToComplaints.setOnClickListener(this);
        confirmSuspension = (Button) findViewById(R.id.btn_confirmSuspension);
        confirmSuspension.setOnClickListener(this);
        ignoreComplaint = (Button) findViewById(R.id.btn_ignoreComplaint);
        ignoreComplaint.setOnClickListener(this);

        typeOfSuspension = (RadioGroup) findViewById(R.id.radioGroupAmountOfTime);
        durationOfSuspension = (RadioGroup) findViewById(R.id.radioGroupSpecifiedTime);

        typeOfSuspension.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radBtn_specificTime) {
                    durationOfSuspension.setVisibility(View.VISIBLE);
                    specificTimeChecked = true;
                } else {
                    durationOfSuspension.setVisibility(View.INVISIBLE);
                    specificTimeChecked = false;
                }
            }
        });

        durationOfSuspension.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radBtn_1year) {
                    suspensionLength = "1year";
                } else if (checkedId == R.id.radBtn_1Week) {
                    suspensionLength = "1week";
                } else if (checkedId == R.id.radBtn_1month) {
                    suspensionLength = "1month";
                } else {
                    suspensionLength = "1day";
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_backToComplaints:
                startActivity(new Intent(this, admin_manage_complaints.class));
                break;
            case R.id.btn_confirmSuspension:
//                confirmSuspension();
                break;
            case R.id.btn_ignoreComplaint:
//                ignoreComplaint();
                break;
        }
    }

    public void setChefName(String chefName) { this.chefName = chefName; }

    public void setClientName(String clientName) { this.clientName = clientName; }
}