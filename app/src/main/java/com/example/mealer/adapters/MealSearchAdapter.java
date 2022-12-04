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
import com.example.mealer.structure.Chef;
import com.example.mealer.structure.Meal;

import java.util.ArrayList;

public class MealSearchAdapter extends ArrayAdapter<Meal> {
    ArrayList<Meal> searchResults;
    ArrayList<Chef> chefs;
    private Activity context;
    private String mealName, mealDescription, mealPrice, chefName;

    public MealSearchAdapter(Activity context, ArrayList<Meal> searchResults, ArrayList<Chef> chefs) {
        super(context, R.layout.menu_adapter, searchResults);
        this.context = context;
        this.searchResults = searchResults;
        this.chefs = chefs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.search_meal_adapter, null, true);

        TextView chefName = listViewItem.findViewById(R.id.chefNameSearch);
        TextView mealName = listViewItem.findViewById(R.id.mealNameSearch);
        TextView mealDescription = listViewItem.findViewById(R.id.mealDescriptionSearch);
        TextView mealPrice = listViewItem.findViewById(R.id.mealPriceSearch);
        TextView mealVegetarian = listViewItem.findViewById(R.id.mealVegetarianSearch);
        TextView mealRating = listViewItem.findViewById(R.id.mealRatingSearch);

        Chef chef = chefs.get(position);
        Meal meal = searchResults.get(position);
        chefName.setText("Meal Offered by: " + chef.getFirstName() + " " + chef.getLastName());
        mealName.setText(meal.getMealName());
        mealDescription.setText(meal.getMealDescription());
        mealPrice.setText("Price: " + meal.getMealPrice() + "$");
        if (meal.getVegetarian()) { mealVegetarian.setVisibility(View.VISIBLE); }
        if (meal.getRating().equals("-1")) {
            mealRating.setText("No Ratings");
        } else {
            mealRating.setText(meal.getRating() + " /5 stars");
        }

        return listViewItem;
    }
}
