package com.example.mealer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mealer.R;
import com.example.mealer.structure.Complaint;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class home_page_admin extends AppCompatActivity implements View.OnClickListener {
    private Button logout, manageComplaints;
    private TextView complaintsAlert;
    private Complaint complaint;
    private ArrayList<Complaint> listOfComplaints;
    private DatabaseReference complaintsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_admin);

        complaintsRef = FirebaseDatabase.getInstance().getReference("Complaints");
        listOfComplaints = new ArrayList<>();

        logout = findViewById(R.id.btnLogoutAdmin);
        logout.setOnClickListener(this);
        manageComplaints = (Button) findViewById(R.id.btn_manageComplaints);
        manageComplaints.setOnClickListener(this);

        complaintsAlert = findViewById(R.id.complaintsMessage);
    }

    private void setComplaintText() {
        if (listOfComplaints.size() == 0) {
            complaintsAlert.setText("No Complaints to Manage!");
        } else {
            complaintsAlert.setText("There are " + listOfComplaints.size() + " complaints. Click Below to Manage");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        complaintsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listOfComplaints.clear();
                for (DataSnapshot ds: snapshot.getChildren()) {
                    //Store all the complaints
                    complaint = ds.getValue(Complaint.class);
                    listOfComplaints.add(complaint);
                }
                setComplaintText();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogoutAdmin:
                startActivity(new Intent(this, homePage.class));
                break;
            case R.id.btn_manageComplaints:
                startActivity(new Intent(this, admin_manage_complaints.class));
        }
    }
}