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
    private DatabaseReference reference;
    private String chefName, clientName;

    public ComplaintAdapter(Activity context, ArrayList<Complaint> complaints) {
        super(context, R.layout.complaint_adapter, complaints);
        this.context = context;
        this.complaints = complaints;
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

        // Get data from database for chefName & clientName using chefID & clientID
        // Set corresponding textView fields to the values respective to the given complaint
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

                title.setText(complaint.getTitle());
                chefName.setText("Complaint Against Chef: " + getChefName());
                description.setText("Description: " + complaint.getDescription());
                clientName.setText("Complaint filed by Client: " + getClientName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return listViewItem;
    }

    public String getChefName() { return chefName; }

    public String getClientName() { return clientName; }

    public void setChefName(String chefName) { this.chefName = chefName; }

    public void setClientName(String clientName) { this.clientName = clientName; }
}
