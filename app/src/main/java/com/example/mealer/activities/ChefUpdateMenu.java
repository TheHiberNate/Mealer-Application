package com.example.mealer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealer.R;
import com.example.mealer.adapters.MenuAdapter;
import com.example.mealer.structure.Meal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChefUpdateMenu extends AppCompatActivity implements View.OnClickListener {
    private TextView noMeals;
    private Button backToMenuHome;
    private ListView menuListView;
    private ArrayList<Meal> menuList;
    private Meal meal;
    private MenuAdapter menuAdapter;
    private String chefID;
    private DatabaseReference menuReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_update_menu);

        menuListView = (ListView) findViewById(R.id.mealList);
        menuList = new ArrayList<>();

        backToMenuHome = (Button) findViewById(R.id.btn_backToMenuHome);
        backToMenuHome.setOnClickListener(this);

        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(getApplicationContext(), "You cannot change the template meal!", Toast.LENGTH_LONG).show();
                } else {
                    Meal meal = menuList.get(position);
                    String mealID = String.valueOf(position);
                    showUpdateDeleteDialog(meal.getMealName(), mealID);
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        chefID = extras.getString("chefID");

        menuReference = FirebaseDatabase.getInstance().getReference("Users").child(chefID).child("menu").child("meals");
        menuAdapter = new MenuAdapter(ChefUpdateMenu.this, menuList);

        noMeals = (TextView) findViewById(R.id.textView_NoMeals);
        menuListView.setEmptyView(noMeals);
    }

    @Override
    protected void onStart() {
        super.onStart();

        menuReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menuList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    meal = ds.getValue(Meal.class);
                    menuList.add(meal);
                }
                menuListView.setAdapter(menuAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_backToMenuHome:
                finish();
        }
    }

    public void showUpdateDeleteDialog(String mealName, String mealID) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_menu_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText newName = (EditText) dialogView.findViewById(R.id.editTextNewMealName);
        final EditText newDescription  = (EditText) dialogView.findViewById(R.id.editTextNewMealDescription);
        final EditText newPrice = (EditText) dialogView.findViewById(R.id.editTextNewMealPrice);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.btnUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.btnDeleteMeal);
        final Switch updateAvailable = (Switch) dialogView.findViewById(R.id.switchUpdateAvailability);
        final Switch updateVegetarian = (Switch) dialogView.findViewById(R.id.switchUpdateVegetarian);

        dialogBuilder.setTitle(mealName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = newName.getText().toString().trim();
                String description = newDescription.getText().toString().trim();
                String price = newPrice.getText().toString();
                Boolean vegetarianChecked = updateVegetarian.isChecked();
                Boolean availableChecked = updateAvailable.isChecked();
                updateMeal(mealID, name, description, price, vegetarianChecked, availableChecked);
                b.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMeal(mealID);
                b.dismiss();
            }
        });
    }

    private boolean deleteMeal(String mealID) {
        menuReference.child(mealID).removeValue();
        Toast.makeText(getApplicationContext(), "Meal Deleted From Menu", Toast.LENGTH_LONG).show();
        return true;
    }

    private void updateMeal(String mealID,String name, String description, String price, Boolean vegetarianChecked, Boolean availableChecked) {
        DatabaseReference mealReference = menuReference.child(mealID);
        if (!name.isEmpty()) {
            mealReference.child("mealName").setValue(name);
        }
        if (!description.isEmpty()) {
            mealReference.child("mealDescription").setValue(description);
        }
        if (!price.isEmpty()) {
            mealReference.child("mealPrice").setValue(price);
        }
        mealReference.child("vegetarian").setValue(vegetarianChecked);
        mealReference.child("available").setValue(availableChecked);
        Toast.makeText(getApplicationContext(), "Meal Updated", Toast.LENGTH_LONG).show();
    }
}