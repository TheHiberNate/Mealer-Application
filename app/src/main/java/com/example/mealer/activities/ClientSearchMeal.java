package com.example.mealer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mealer.R;
import com.example.mealer.adapters.MealSearchAdapter;
import com.example.mealer.structure.Chef;
import com.example.mealer.structure.Meal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class ClientSearchMeal extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private String[] categories = {"No Filter", "Chef", "Meal", "Vegetarian"};
    private String searchFilter, searchText;
    private Spinner options;
    private EditText searchEditText;
    private TextView noMeals;
    private ListView searchResultsListView;
    private Button searchBtn, backBtn;
    private ArrayList<Meal> mealList;
    private ArrayList<Chef> chefList;
    private ArrayList<String> listMealID, listChefID;
    private DatabaseReference reference;
    private MealSearchAdapter mealSearchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_search_meal);

        initializeVariables();
    }

    private void initializeVariables() {
        // For the dropdown options (spinner)
        options = findViewById(R.id.searchFilter);
        options.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        options.setAdapter(adapter);

        searchResultsListView = findViewById(R.id.ListViewSearchResults);
        searchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // send information with intents to next page
                // start new activity to order meals

            }
        });

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

        mealSearchAdapter = new MealSearchAdapter(ClientSearchMeal.this, mealList, chefList);

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
                    Boolean suspended = (Boolean) ds1.child("suspended").getValue();
                    if (role.equals("Chef") && Boolean.FALSE.equals(suspended)) {
                        System.out.println(role + " " + suspended);
                        Chef chef = ds1.getValue(Chef.class);
                        String chefID = ds1.getKey();
                        DataSnapshot mealSnapshot = ds1.child("menu").child("meals");
                        for (DataSnapshot ds2 : mealSnapshot.getChildren()) {
                            Boolean available = (Boolean) ds2.child("available").getValue();
                            if (!ds2.getKey().equals("0") && available) {
                                Meal meal = ds2.getValue(Meal.class);
                                mealList.add(meal);
//                                    System.out.println(meal.getMealName());
                                listMealID.add(ds2.getKey());
                                chefList.add(chef);
                                listChefID.add(chefID);
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
        searchText = searchEditText.getText().toString().toLowerCase(Locale.ROOT);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chefList.clear();
                listChefID.clear();
                mealList.clear();
                listMealID.clear();
                for (DataSnapshot ds1 : snapshot.getChildren()) {
                    String role = (String) ds1.child("role").getValue();
                    Boolean suspended = (Boolean) ds1.child("suspended").getValue();
                    if (role.equals("Chef") && Boolean.FALSE.equals(suspended)) {
                        Chef chef = ds1.getValue(Chef.class);
                        String chefID = ds1.getKey();

                        // Search Filter by Chef (first name or last name)
                        if (searchFilter.equals(categories[1])) {
                            String chefFirstName = chef.getFirstName().toLowerCase(Locale.ROOT);
                            String chefLastName = chef.getLastName().toLowerCase(Locale.ROOT);

                            if (chefFirstName.startsWith(searchText) || chefLastName.startsWith(searchText)) {
                                DataSnapshot mealSnapshot = ds1.child("menu").child("meals");
                                for (DataSnapshot ds2 : mealSnapshot.getChildren()) {
                                    Boolean available = (Boolean) ds2.child("available").getValue();
                                    if (!ds2.getKey().equals("0") && available) {
                                        Meal meal = ds2.getValue(Meal.class);
                                        mealList.add(meal);
//                                    System.out.println(meal.getMealName());
                                        listMealID.add(ds2.getKey());
                                        chefList.add(chef);
                                        listChefID.add(chefID);
                                    }
                                }
                            }
                        }

                        // Search Filter by Meal Name
                        else if (searchFilter.equals(categories[2])) {
                            DataSnapshot mealSnapshot = ds1.child("menu").child("meals");
                            for (DataSnapshot ds2 : mealSnapshot.getChildren()) {
                                Boolean available = (Boolean) ds2.child("available").getValue();
                                if (!ds2.getKey().equals("0") && available) {
                                    Meal meal = ds2.getValue(Meal.class);
                                    String mealName = meal.getMealName().toLowerCase(Locale.ROOT);
                                    if (mealName.startsWith(searchText)) {
                                        mealList.add(meal);
                                        listMealID.add(ds2.getKey());
                                        chefList.add(chef);
                                        listChefID.add(chefID);
                                    }
                                }
                            }
                        }

                        // Search Filter by Vegetarian Meal
                        else if (searchFilter.equals(categories[3])) {
                            DataSnapshot mealSnapshot = ds1.child("menu").child("meals");
                            for (DataSnapshot ds2 : mealSnapshot.getChildren()) {
                                Boolean available = (Boolean) ds2.child("available").getValue();
                                if (!ds2.getKey().equals("0") && available) {
                                    Meal meal = ds2.getValue(Meal.class);
                                    Boolean vegetarianMeal = meal.getVegetarian();
                                    String mealName = meal.getMealName().toLowerCase(Locale.ROOT);
                                    String chefFirstName = chef.getFirstName().toLowerCase(Locale.ROOT);
                                    String chefLastName = chef.getLastName().toLowerCase(Locale.ROOT);
                                    if (vegetarianMeal & (mealName.startsWith(searchText) || chefFirstName.startsWith(searchText) || chefLastName.startsWith(searchText))) {
                                        mealList.add(meal);
                                        listMealID.add(ds2.getKey());
                                        chefList.add(chef);
                                        listChefID.add(chefID);
                                    }
                                }
                            }
                        }

                        // No Search Filter
                        else {
                            DataSnapshot mealSnapshot = ds1.child("menu").child("meals");
                            for (DataSnapshot ds2 : mealSnapshot.getChildren()) {
                                Boolean available = (Boolean) ds2.child("available").getValue();
                                if (!ds2.getKey().equals("0") && available) {
                                    Meal meal = ds2.getValue(Meal.class);
                                    mealList.add(meal);
                                    listMealID.add(ds2.getKey());
                                    chefList.add(chef);
                                    listChefID.add(chefID);
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