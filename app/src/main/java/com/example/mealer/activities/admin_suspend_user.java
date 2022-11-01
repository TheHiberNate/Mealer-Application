package com.example.mealer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mealer.R;

public class admin_suspend_user extends AppCompatActivity {
    private Button backToComplaints;
    private RadioGroup typeOfSuspension, durationOfSuspension;
    private TextView chefName, clientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_suspend_user);
    }
}