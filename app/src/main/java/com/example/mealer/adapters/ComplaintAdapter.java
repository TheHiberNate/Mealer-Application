package com.example.mealer.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mealer.R;
import com.example.mealer.structure.Complaint;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ComplaintAdapter extends ArrayAdapter<Complaint> {

    ArrayList<Complaint> complaints;
    private Activity context;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

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

        title.setText(complaint.getTitle());
        chefName.setText("Complaint Against Chef: " + database.getReference(complaint.getChefID()).child("firstname") + database.getReference(complaint.getChefID()).child("lastname"));
        description.setText(complaint.getDescription());
        clientName.setText("Complaint filed by Client: " + complaint.getClientID());

        return listViewItem;
    }
}
