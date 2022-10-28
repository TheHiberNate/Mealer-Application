package com.example.mealer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mealer.R;
import com.example.mealer.structure.Client;
import com.example.mealer.structure.Complaint;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class admin_manage_complaints extends AppCompatActivity {

    ListView complaintsListView;
    ArrayList<String> complaints;
    DatabaseReference complaintsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_complaints);

        complaintsReference = FirebaseDatabase.getInstance().getReference("Complaints");
        complaintsListView = (ListView) findViewById(R.id.complaintsListView);
        complaints = new ArrayList<>();
        for (int i =0; i < 20; i++) {
            complaints.add("Complaint " + (i+1));
        }

        ArrayAdapter<String> complaintAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, complaints);
        complaintsListView.setAdapter(complaintAdapter);
    }
}