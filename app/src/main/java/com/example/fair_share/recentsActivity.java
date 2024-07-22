package com.example.fair_share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class recentsActivity extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recents);

        linearLayout = findViewById(R.id.linearLayout);
        addRestaurantsToLayout();
    }

    private void addRestaurantsToLayout() {
        LayoutInflater inflater = LayoutInflater.from(this);

        // Sample random restaurant names and images
        String[] restaurantNames = {
                "Zaatar W Zeit",
                "Roadster",
                "Cheese On Top",
                "Uniun",
                "Crepaway",
                "Deek Duke"
        };

        String[] dates = {
                "01/01/2024",
                "02/15/2024",
                "03/30/2024",
                "04/10/2024",
                "05/20/2024",
                "06/25/2024"
        };

        int[] imageResources = {
                R.drawable.rec1, // Replace with actual images
                R.drawable.rec2,
                R.drawable.rec3,
                R.drawable.rec1,
                R.drawable.rec2,
                R.drawable.rec3
        };


        //loops through the items and displays them on screen
        for (int i = 0; i < restaurantNames.length; i++) {
            View itemView = inflater.inflate(R.layout.item_restaurant, linearLayout, false);

            ImageView imageView = itemView.findViewById(R.id.imageView);
            TextView textViewRestaurantName = itemView.findViewById(R.id.textViewRestaurantName);
            TextView textViewDate = itemView.findViewById(R.id.textViewDate);

            imageView.setImageResource(imageResources[i]);
            textViewRestaurantName.setText(restaurantNames[i]);
            textViewDate.setText(dates[i]);

            linearLayout.addView(itemView);
        }
    }
}
