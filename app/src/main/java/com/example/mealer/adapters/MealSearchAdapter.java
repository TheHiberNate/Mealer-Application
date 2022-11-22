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

import java.util.ArrayList;

public class MealSearchAdapter extends ArrayAdapter<Meal> {
    ArrayList<Meal> searchResults;
    private Activity context;
    private String mealName, mealDescription, mealPrice;

    public MealSearchAdapter(Activity context, ArrayList<Meal> searchResults) {
        super(context, R.layout.menu_adapter, searchResults);
        this.context = context;
        this.searchResults = searchResults;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.search_meal_adapter, null, true);

        TextView mealName = listViewItem.findViewById(R.id.mealNameSearch);
        TextView mealDescription = listViewItem.findViewById(R.id.mealDescriptionSearch);
        TextView mealPrice = listViewItem.findViewById(R.id.mealPriceSearch);
        TextView mealVegetarian = listViewItem.findViewById(R.id.mealVegetarianSearch);

        Meal meal = searchResults.get(position);
        mealName.setText(meal.getMealName());
        mealDescription.setText(meal.getMealDescription());
        mealPrice.setText(meal.getMealPrice());
        if (meal.getVegetarian()) { mealVegetarian.setVisibility(View.VISIBLE); }

        return listViewItem;
    }
}
