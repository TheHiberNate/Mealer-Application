package com.example.mealer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.example.mealer.structure.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ClientOrderFood extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private String[] categories = {"No Filter", "Chef", "Meal", "Vegetarian"};
    private String searchFilter, mealID, chefID;
    private Spinner options;
    private EditText searchEditText;
    private TextView noMeals;
    private ListView searchResultsListView;
    private Button searchBtn, backBtn;
    private ArrayList<Meal> mealList;
    private ArrayList<Chef> chefList;
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
        chefList = new ArrayList<>();
        listChefID = new ArrayList<>();
        listMealID = new ArrayList<>(); // id for the chefs of the search result meals

        reference = FirebaseDatabase.getInstance().getReference("Users");

        mealSearchAdapter = new MealSearchAdapter(ClientOrderFood.this, mealList, chefList);

        noMeals = findViewById(R.id.textViewNoMeals);
        searchResultsListView.setEmptyView(noMeals);

    }

    @Override
    protected void onStart() {
        super.onStart();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chefList.clear();
                listChefID.clear();
                mealList.clear();
                listMealID.clear();

                for (DataSnapshot ds1 : snapshot.getChildren()) {
                    String role = (String) ds1.child("role").getValue();
                    if (role.equals("Chef")) {
                        Boolean suspended = (Boolean) ds1.child("suspended").getValue();
                        System.out.println(role + " " + suspended);
                        if (!suspended) {
                            chef = snapshot.getValue(Chef.class);
                            chefID = snapshot.getKey();
                            DataSnapshot mealSnapshot = ds1.child(chefID).child("menu").child("meals");
                            for (DataSnapshot ds2 : mealSnapshot.getChildren()) {
                                if (!ds2.getKey().equals("0")) {
                                    meal = ds2.getValue(Meal.class);
                                    mealList.add(meal);
                                    System.out.println(meal.getMealName());
                                    listMealID.add(ds2.getKey());
                                    chefList.add(chef);
                                    listChefID.add(chefID);
                                }
                            }
                        }
                    }
                }
                searchResultsListView.setAdapter(mealSearchAdapter);

//                chef = snapshot.child("ISR9Rk2I0JTMXfw3oAT3XlcBEjD2").getValue(Chef.class);
//                chefList.add(chef);
//                chefList.add(chef);
//                chefList.add(chef);
//                listChefID.add("ISR9Rk2I0JTMXfw3oAT3XlcBEjD2");
//                listChefID.add("ISR9Rk2I0JTMXfw3oAT3XlcBEjD2");
//                listChefID.add("ISR9Rk2I0JTMXfw3oAT3XlcBEjD2");
//
//                DataSnapshot mealSnapshot = snapshot.child("ISR9Rk2I0JTMXfw3oAT3XlcBEjD2").child("menu").child("meals");
//                for (DataSnapshot ds : mealSnapshot.getChildren()) {
//                    if (!ds.getKey().equals("0")) {
//                        meal = ds.getValue(Meal.class);
//                        mealList.add(meal);
//                        listMealID.add(ds.getKey());
//                    }
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    if (!ds.getKey().equals("0")) {
//                        meal = ds.getValue(Meal.class);
//                        mealList.add(meal);
//                        listMealID.add(ds.getKey());
//                    }
//                }
//                searchResultsListView.setAdapter(mealSearchAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
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
        searchFilter = searchEditText.getText().toString();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mealList.clear();
                listMealID.clear();
                listChefID.clear();

                if (searchFilter.equals(categories[0])) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        chef = ds.getValue(Chef.class);
                        chefID = ds.getKey();
                        if (ds.child("role").getValue().equals("Chef") && ds.child("suspended").getValue().equals(false)) {
                            DataSnapshot meals = ds.child("menu").child("meals");
                            for (DataSnapshot n : meals.getChildren()) {
                                if (!n.getKey().equals("0")) {
                                    mealID = ds.getKey();
                                    meal = ds.getValue(Meal.class);
                                    mealList.add(meal);
                                    chefList.add(chef);
                                    listMealID.add(mealID);
                                    listChefID.add(chefID);
                                    System.out.println(meal.getMealName());
                                }
                            }
                        }
                    }
                }

                searchResultsListView.setAdapter(mealSearchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}