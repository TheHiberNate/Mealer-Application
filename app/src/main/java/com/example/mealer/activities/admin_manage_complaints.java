package com.example.mealer.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mealer.R;
import com.example.mealer.structure.Client;
import com.example.mealer.structure.Complaint;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class admin_manage_complaints extends AppCompatActivity {

    private ListView complaintsListView;
    private ArrayList<String> complaintsArrayList;
    private DatabaseReference complaintsReference;
    private FirebaseAuth mAuth;
    private Complaint complaint;
    private ArrayAdapter<String> complaintAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_complaints);

        complaint = new Complaint();
        // initialize list view & array list
        complaintsListView = (ListView) findViewById(R.id.complaintsListView);
        complaintsArrayList = new ArrayList<>();
        complaintsReference = FirebaseDatabase.getInstance().getReference("Complaints");
        complaintAdapter = new ArrayAdapter<>(this, R.layout.complaint_info, R.id.complaintInfo, complaintsArrayList);

    }

    @Override
    protected void onStart() {
        super.onStart();

        complaintsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaintsArrayList.clear();
                for (DataSnapshot ds: snapshot.getChildren()) {
                    complaint = ds.getValue(Complaint.class);
                    complaintsArrayList.add(complaint.getTitle() + ": \n" + complaint.getDescription());
                }
                complaintsListView.setAdapter(complaintAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}