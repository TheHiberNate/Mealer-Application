package com.example.mealer.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mealer.R;
import com.example.mealer.structure.Meal;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MenuAdapter extends ArrayAdapter<Meal> {
    ArrayList<Meal> meals;
    private Activity context;
    private String mealName, mealDescription, mealPrice;

    public MenuAdapter(Activity context, ArrayList<Meal> meals) {
        super(context, R.layout.menu_adapter, meals);
        this.context = context;
        this.meals = meals;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.menu_adapter, null, true);

        TextView mealName = listViewItem.findViewById(R.id.mealTitle);
        TextView mealDescription = listViewItem.findViewById(R.id.mealDescription);
        TextView mealPrice = listViewItem.findViewById(R.id.mealPrice);
        TextView mealVegetarian = listViewItem.findViewById(R.id.mealVegetarian);
        TextView mealAvailable = listViewItem.findViewById(R.id.mealAvailability);

        Meal meal = meals.get(position);
        mealName.setText(meal.getMealName());
        mealDescription.setText(meal.getMealDescription());
        mealPrice.setText(meal.getMealPrice());
        if (meal.getVegetarian()) { mealVegetarian.setVisibility(View.VISIBLE); }
        if (meal.getAvailable()) { mealAvailable.setText("Meal is Available"); }

        return listViewItem;
    }
}
