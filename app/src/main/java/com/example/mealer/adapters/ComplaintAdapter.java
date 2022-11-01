package com.example.mealer.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mealer.R;
import com.example.mealer.structure.Complaint;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ComplaintAdapter extends ArrayAdapter<Complaint> {

    ArrayList<Complaint> complaints;
    private Activity context;
    private FirebaseDatabase database1, database2;
    private String chefFirstName, chefLastName, clientFirstName, clientLastName;

    public ComplaintAdapter(Activity context, ArrayList<Complaint> complaints) {
        super(context, R.layout.complaint_adapter, complaints);
        this.context = context;
        this.complaints = complaints;
//        this.chefFirstName = chefFirstName;
//        this.chefLastName = chefLastName;
//        this.clientFirstName = clientFirstName;
//        this.clientLastName = clientLastName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.complaint_adapter, null, true);

        TextView title = listViewItem.findViewById(R.id.textView_titleComplaint);
        TextView chefName = listViewItem.findViewById(R.id.textView_ComplaintChefName);
        TextView description = listViewItem.findViewById(R.id.textView_complaintDescription);
        TextView clientName = listViewItem.findViewById(R.id.textView_complaintClientName);

        Complaint complaint = complaints.get(position);
        String chefID = complaint.getChefID();
        String clientID = complaint.getClientID();


        database1 = FirebaseDatabase.getInstance();
        database2 = FirebaseDatabase.getInstance();

        DatabaseReference referenceChef = database1.getReference("Users").child(chefID);
        DatabaseReference referenceClient = database2.getReference("Users").child(clientID);

        referenceChef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String tempchefFirstName = snapshot.child("firstName").getValue().toString();
                final String tempchefLastName = snapshot.child("lastName").getValue().toString();
                setChefFirstName(tempchefFirstName);
                setChefLastName(tempchefLastName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        referenceClient.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String tempclientFirstName = snapshot.child("firstName").getValue().toString();
                final String tempclientLastName = snapshot.child("lastName").getValue().toString();
                setClientFirstName(tempclientFirstName);
                setClientLastName(tempclientLastName);

                title.setText(complaint.getTitle());
                chefName.setText("Complaint Against Chef: " + getChefFirstName() + " " + getChefLastName());
                description.setText("Description: " + complaint.getDescription());
                clientName.setText("Complaint filed by Client: " + getClientFirstName() + " " + getClientLastName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return listViewItem;
    }

    public String getChefFirstName() { return chefFirstName; }

    public void setChefFirstName(String chefFirstName) { this.chefFirstName = chefFirstName; }

    public String getChefLastName() { return chefLastName; }

    public void setChefLastName(String chefLastName) { this.chefLastName = chefLastName; }

    public String getClientFirstName() { return clientFirstName; }

    public void setClientFirstName(String clientFirstName) { this.clientFirstName = clientFirstName; }

    public String getClientLastName() { return clientLastName; }

    public void setClientLastName(String clientLastName) { this.clientLastName = clientLastName; }
}
