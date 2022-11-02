package com.example.mealer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mealer.R;
import com.example.mealer.adapters.ComplaintAdapter;
import com.example.mealer.structure.Complaint;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_manage_complaints extends AppCompatActivity {

    private ListView complaintsListView;
    private TextView noComplaints;
    private ArrayList<Complaint> complaints;
    private DatabaseReference complaintsReference;
    private Complaint complaint;
    private ComplaintAdapter complaintAdapter;
    private String chefName, clientName;

    public admin_manage_complaints() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_complaints);

        complaint = new Complaint();
        // initialize list view & array list
        complaintsListView = (ListView) findViewById(R.id.complaintsListView);
        complaints = new ArrayList<>();
        complaintsReference = FirebaseDatabase.getInstance().getReference("Complaints");
        complaintAdapter = new ComplaintAdapter(admin_manage_complaints.this, complaints);

        complaintsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                setChefName(complaintAdapter.getChefFirstName() + " " + complaintAdapter.getChefLastName());
                setClientName(complaintAdapter.getClientFirstName() + " " + complaintAdapter.getClientLastName());
                System.out.println(chefName);
                System.out.println(clientName);

//                complaints.get(position).getChefID();

                Intent intent = new Intent(admin_manage_complaints.this, admin_suspend_user.class);
//                intent.putExtra("chefName", complaintAdapter.getChefFirstName() + " " + complaintAdapter.getChefLastName());
//                intent.putExtra("clientName", complaintAdapter.getClientFirstName() + " " + complaintAdapter.getClientLastName());
                startActivity(intent);

            }
        });

        noComplaints = (TextView) findViewById(R.id.textView_NoComplaints);
        complaintsListView.setEmptyView(noComplaints);
    }

    @Override
    protected void onStart() {
        super.onStart();

        complaintsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaints.clear();
                for (DataSnapshot ds: snapshot.getChildren()) {
                    complaint = ds.getValue(Complaint.class);
                    complaints.add(complaint);
                }
                complaintsListView.setAdapter(complaintAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public String getClientName() { return clientName; }

    public String getChefName() { return chefName; }

    public void setClientName(String clientName) { this.clientName = clientName; }

    public void setChefName(String chefName) { this.chefName = chefName; }
}