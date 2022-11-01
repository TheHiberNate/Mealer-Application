package com.example.mealer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mealer.R;

public class admin_suspend_user extends AppCompatActivity implements View.OnClickListener {
    private TextView chefName, clientName;
    private Button backToComplaints, confirmSuspension, ignoreComplaint;
    private RadioButton indefiniteTime, specificTime, oneDay, oneWeek, oneMonth, oneYear;
    private RadioGroup typeOfSuspension, durationOfSuspension;
    private Boolean specificTimeChecked;
    private String suspensionLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_suspend_user);

        initializeVariables();
    }

    private void initializeVariables() {
        chefName = (TextView) findViewById(R.id.textViewChefName);
        clientName = (TextView) findViewById(R.id.textViewClientName);

        backToComplaints = (Button) findViewById(R.id.btn_backToComplaints);
        backToComplaints.setOnClickListener(this);
        confirmSuspension = (Button) findViewById(R.id.btn_confirmSuspension);
        confirmSuspension.setOnClickListener(this);
        ignoreComplaint = (Button) findViewById(R.id.btn_ignoreComplaint);
        ignoreComplaint.setOnClickListener(this);

        indefiniteTime = (RadioButton) findViewById(R.id.radbtn_indefiniteTime);
        specificTime = (RadioButton) findViewById(R.id.radBtn_specificTime);
        oneDay = (RadioButton) findViewById(R.id.radBtn_1Day);
        oneWeek = (RadioButton) findViewById(R.id.radBtn_1Week);
        oneMonth = (RadioButton) findViewById(R.id.radBtn_1month);
        oneYear = (RadioButton) findViewById(R.id.radBtn_1year);

        typeOfSuspension = (RadioGroup) findViewById(R.id.radioGroupAmountOfTime);
        durationOfSuspension = (RadioGroup) findViewById(R.id.radioGroupSpecifiedTime);

        typeOfSuspension.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radBtn_specificTime) {
                    durationOfSuspension.setVisibility(View.VISIBLE);
                    specificTimeChecked = true;
                } else {
                    durationOfSuspension.setVisibility(View.INVISIBLE);
                    specificTimeChecked = false;
                }
            }
        });

        durationOfSuspension.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radBtn_1year) {
                    suspensionLength = "1year";
                } else if (checkedId == R.id.radBtn_1Week) {
                    suspensionLength = "1week";
                } else if (checkedId == R.id.radBtn_1month) {
                    suspensionLength = "1month";
                } else {
                    suspensionLength = "1day";
                }
            }
        });

    }


    @Override
    public void onClick(View v) {

    }
}