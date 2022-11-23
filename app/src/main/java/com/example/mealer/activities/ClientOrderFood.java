package com.example.mealer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealer.R;
import com.example.mealer.adapters.MealSearchAdapter;
import com.example.mealer.adapters.MenuAdapter;
import com.example.mealer.structure.Chef;
import com.example.mealer.structure.Complaint;
import com.example.mealer.structure.Meal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClientOrderFood extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private String[] categories = {"No Filter", "Chef", "Meal", "Vegetarian"};
    private String searchFilter, mealID;
    private Spinner options;
    private EditText searchEditText;
    private TextView noMeals;
    private ListView searchResultsListView;
    private Button searchBtn, backBtn;
    private ArrayList<Meal> mealList;
    private ArrayList<String> listMealID, listChefID;
    private Meal meal;
    private Chef chef;
    private DatabaseReference reference;
    private MealSearchAdapter mealSearchAdapter;


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

        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(this);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        searchEditText = findViewById(R.id.NavigatorSearch);

        mealList = new ArrayList<>();
        listChefID = new ArrayList<>();
        listMealID = new ArrayList<>(); // id for the chefs of the search result meals

        reference = FirebaseDatabase.getInstance().getReference("Users");
        mealSearchAdapter = new MealSearchAdapter(ClientOrderFood.this, mealList);

        noMeals = findViewById(R.id.textViewNoMeals);
        searchResultsListView.setEmptyView(noMeals);

    }

    @Override
    protected void onStart() {
        super.onStart();
        searchFilter = searchEditText.getText().toString();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mealList.clear();
                mealList.clear();
                chef.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("role").getValue().equals("Chef") && ds.child("suspended").getValue().equals(false)) {
                        // No Filter search criteria
                        if (searchFilter.equals(categories[0])) {
                            DataSnapshot meals = ds.child("menu").child("meals");
                            for (DataSnapshot n : meals.getChildren()) {
                                if (!n.getKey().equals("0")) {
                                    mealID = ds.getKey();
                                    meal = ds.getValue(Meal.class);
                                    mealList.add(meal);
                                    listMealID.add(mealID);
                                }
                            }
                        }
                        chef = ds.getValue(Chef.class);
                    }
                }
                searchResultsListView.setAdapter(mealSearchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { searchFilter = categories[position]; }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

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