package com.example.mealer.activities;

import androidx.appcompat.app.AppCompatActivity;

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

public class ClientOrderFood extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String [] categories = {"No Filter", "Chef","Meal","Vegetarian"};
    private String searchFilter;
    private Spinner options;
    private TextView noMeals;
    private ListView searchResultsListView;
    private Button searchBtn;



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

        searchResultsListView = (ListView) findViewById(R.id.ListViewSearchResults);

        noMeals = (TextView) findViewById(R.id.textViewNoMeals);
        searchResultsListView.setEmptyView(noMeals);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        searchFilter = categories[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}