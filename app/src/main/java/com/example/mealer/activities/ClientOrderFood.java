package com.example.mealer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealer.R;
import com.example.mealer.structure.Complaint;
import com.example.mealer.structure.Meal;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ClientOrderFood extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private String[] categories = {"No Filter", "Chef", "Meal", "Vegetarian"};
    private String searchFilter;
    private Spinner options;
    private TextView noMeals;
    private ListView searchResultsListView;
    private Button searchBtn, backBtn;
    private ArrayList<Meal> mealList;
    private ArrayList<String> mealIDs;
    private ArrayList<String> chefIDs;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_order_food);

        initializeVariables();
    }

    private void initializeVariables() {
        // For the dropdown options (spinner)
        options = findViewById(R.id.searchFilter);
        options.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        options.setAdapter(adapter);

        searchResultsListView = findViewById(R.id.ListViewSearchResults);

        noMeals = findViewById(R.id.textViewNoMeals);
        searchResultsListView.setEmptyView(noMeals);

        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(this);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        mealList = new ArrayList<>();
        mealIDs = new ArrayList<>();
        chefIDs = new ArrayList<>(); // id for the chefs of the search result meals

        reference = FirebaseDatabase.getInstance().getReference("Users");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        searchFilter = categories[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                finish();
                break;
            case R.id.searchBtn:
                searchMeals();
                break;
        }
    }

    private void searchMeals() {

    }
}