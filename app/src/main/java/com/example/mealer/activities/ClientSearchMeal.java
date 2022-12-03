package com.example.mealer.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mealer.R;
import com.example.mealer.adapters.MealSearchAdapter;
import com.example.mealer.structure.Chef;
import com.example.mealer.structure.Client;
import com.example.mealer.structure.Meal;
import com.example.mealer.structure.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class ClientSearchMeal extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private String[] categories = {"No Filter", "Chef", "Meal", "Vegetarian"};
    private String searchFilter, searchText, clientID;
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
    private DatabaseReference clientRef;
    private Client client;


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

        Bundle extras = getIntent().getExtras();
        clientID = extras.getString("clientID");

        clientRef = FirebaseDatabase.getInstance().getReference("Users").child(clientID);
        clientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                client = snapshot.getValue(Client.class);
                System.out.println(client.getFirstName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchResultsListView = findViewById(R.id.ListViewSearchResults);
        searchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // send information with intents to next page
                // start new activity to order meals
                Chef chef = chefList.get(position);
                String chefID = listChefID.get(position);
                Meal meal = mealList.get(position);
//                String mealRating = meal.getRating();
                String mealID = listMealID.get(position);
                showOrderMealDialog(chef, chefID, meal, mealID, position); // +meal rating
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Search Filter Selection For Dropdown menu
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
                                    String mealName = meal.getMealName().toLowerCase(Locale.ROOT);
                                    String chefFirstName = chef.getFirstName().toLowerCase(Locale.ROOT);
                                    String chefLastName = chef.getLastName().toLowerCase(Locale.ROOT);
                                    if (mealName.startsWith(searchText) || chefFirstName.startsWith(searchText) || chefLastName.startsWith(searchText)) {
                                        mealList.add(meal);
                                        listMealID.add(ds2.getKey());
                                        chefList.add(chef);
                                        listChefID.add(chefID);
                                    }
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

    public void showOrderMealDialog(Chef chef, String chefID, Meal meal, String mealID, int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.order_meal_dialog, null);
        dialogBuilder.setView(dialogView);

        final TextView mealName = (TextView) dialogView.findViewById(R.id.txtViewMealName);
        final TextView chefName = (TextView) dialogView.findViewById(R.id.txtViewChefName);
        final TextView mealPrice = (TextView) dialogView.findViewById(R.id.txtViewMealPrice);
        final TextView mealRating = (TextView) dialogView.findViewById(R.id.txtViewMealRating);
        final EditText mealQuantity = (EditText) dialogView.findViewById(R.id.editTxtQuantity);
        final Button buttonOrder = (Button) dialogView.findViewById(R.id.btnOrder);
        final Button buttonBack = (Button) dialogView.findViewById(R.id.btnBack);

        final String nameChef = chef.getFirstName() + " " + chef.getLastName();
        mealName.setText(" " + meal.getMealName());
        chefName.setText("  Offered by Chef " + nameChef);
        mealPrice.setText("  Price: " + meal.getMealPrice() + "$");
//        mealRating.setText("  Rating: " + meal.getRating());

//        dialogBuilder.setTitle("Order Meal");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderQuantity = mealQuantity.getText().toString();
                if (orderQuantity.isEmpty() || orderQuantity.equals("0")) {
                    mealQuantity.setError("Must Specify Quantity! (Cannot be 0)");
                    mealQuantity.requestFocus();
                } else {
                    orderMeal(chef, chefID, meal, mealID, Integer.valueOf(orderQuantity),position);
                    b.dismiss();
                    createAlertDialog(nameChef);
                }

            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }

    private void createAlertDialog(String nameChef) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ClientSearchMeal.this);

        builder.setCancelable(true);
        builder.setTitle("Order Sent to Chef");
        builder.setMessage("Order has been sent to Chef " + nameChef + "! To see the status of your order, click on the 'My Orders' button on home page");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


    private void orderMeal(Chef chef, String chefID, Meal meal, String mealID, int orderQuantity,int position) {
        Order order = new Order(meal, orderQuantity);
        client.addOrder(order);
        chef.addOrder(order);

    }

    // TRYING TO HAVE NOTIFICATION POPUP
    //    @RequiresApi(api = Build.VERSION_CODES.S)
//    private void orderConfirmationNotification(String nameChef) {
//        String id = "order_id";
//        NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel notifChannel = notifManager.getNotificationChannel(id);
//            if (notifChannel == null) {
//                notifChannel = new NotificationChannel(id, "Order Sent to Chef", NotificationManager.IMPORTANCE_HIGH);
//                // info of notification
//                notifChannel.setDescription("Your order has been set to the chef!" + " Chef will confirm order soon...");
//                notifChannel.enableVibration(true);
//                notifChannel.setVibrationPattern(new long[]{100, 1000, 200, 340});
//                notifChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//                notifManager.createNotificationChannel(notifChannel);
//            }
//        }
//        Intent notifIntent = new Intent(this, NotificationActivity.class);
//        notifIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notifIntent, PendingIntent.FLAG_MUTABLE);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id)
//                .setSmallIcon(R.drawable.food_mealer)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.food_mealer))
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                .bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.food_mealer)).bigLargeIcon(null))
//                .setContentTitle("Order Confirmation")
//                .setContentText("Your order has been set to chef " + nameChef + "!")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setVibrate(new long[]{100, 1000, 200, 340})
//                .setAutoCancel(false)
//                .setTicker("Notification");
//        builder.setContentIntent(contentIntent);
//        NotificationManagerCompat m = NotificationManagerCompat.from(getApplicationContext());
//        m.notify(new Random().nextInt(), builder.build());
//        System.out.println("Got here");
//
//    }

}