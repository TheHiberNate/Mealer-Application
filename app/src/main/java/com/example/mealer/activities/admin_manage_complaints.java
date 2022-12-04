package com.example.mealer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mealer.R;
import com.example.mealer.adapters.ComplaintAdapter;
import com.example.mealer.structure.Complaint;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_manage_complaints extends AppCompatActivity implements View.OnClickListener {

    private ListView complaintsListView;
    private TextView noComplaints, backHome;
    private ArrayList<Complaint> complaintsList;
    private ArrayList<String> complaintsIDList;
    private DatabaseReference complaintsReference;
    private Complaint complaint;
    private ComplaintAdapter complaintAdapter;
    private String chefName, clientName;
    private String chefID, clientID, complaintID;

    public admin_manage_complaints() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_complaints);

        backHome = (TextView) findViewById(R.id.textView_BacktoAdminHome);
        backHome.setOnClickListener(this);

        complaint = new Complaint();
        // initialize list view & array list
        complaintsListView = (ListView) findViewById(R.id.complaintsListView);
        complaintsList = new ArrayList<>();
        complaintsIDList = new ArrayList<>();
        complaintsReference = FirebaseDatabase.getInstance().getReference("Complaints");
        complaintAdapter = new ComplaintAdapter(admin_manage_complaints.this, complaintsList);

        complaintsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                chefID = complaintsList.get(position).getChefID();
                clientID = complaintsList.get(position).getClientID();
                complaintID = complaintsIDList.get(position);

                Intent intent = new Intent(admin_manage_complaints.this, admin_suspend_user.class);
                intent.putExtra("chefName", chefID);
                intent.putExtra("clientName", clientID);
                intent.putExtra("complaintID", complaintID);

                startActivity(intent);
            }
        });

        noComplaints = (TextView) findViewById(R.id.noOrderTxtView);
        complaintsListView.setEmptyView(noComplaints);
    }

    @Override
    protected void onStart() {
        super.onStart();

        complaintsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaintsList.clear();
                complaintsIDList.clear();
                for (DataSnapshot ds: snapshot.getChildren()) {
                    //Store all the complaints
                    complaint = ds.getValue(Complaint.class);
                    complaintsList.add(complaint);

                    //Store all the complaintID's
                    complaintsIDList.add(ds.getKey());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_BacktoAdminHome:
                startActivity(new Intent(this, home_page_admin.class));
                break;
        }
    }
}