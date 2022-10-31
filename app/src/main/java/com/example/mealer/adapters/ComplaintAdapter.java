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

        DatabaseReference referenceChef = database1.getReference(chefID);
        DatabaseReference referenceClient = database2.getReference(clientID);

        referenceChef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chefFirstName = snapshot.child("firstName").getValue().toString();
                chefLastName = snapshot.child("lastName").getValue().toString();
                System.out.println(chefFirstName+chefLastName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        referenceClient.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clientFirstName = snapshot.child("firstName").getValue().toString();
                clientLastName = snapshot.child("lastName").getValue().toString();
                System.out.println(clientFirstName+clientLastName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        title.setText(complaint.getTitle());
        chefName.setText("Complaint Against Chef: " + chefFirstName + " " + chefLastName);
        description.setText(complaint.getDescription());
        clientName.setText("Complaint filed by Client: " + clientFirstName + " " + clientFirstName);

        return listViewItem;
    }
}
