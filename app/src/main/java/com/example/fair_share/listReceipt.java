package com.example.fair_share;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.LinearLayout;

public class listReceipt extends AppCompatActivity {

    private LinearLayout buttonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reciept);

        //initialize views
        buttonContainer = findViewById(R.id.buttonContainer);

        // gets values from numberpicker in takeCameraView
        int selectedValue = getIntent().getIntExtra("selectedValue", 4);

        //start number picker
        updateButtons(selectedValue);
    }

    private void updateButtons(int count) {
        buttonContainer.removeAllViews();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int spacingInPixels = (int) (2 * displayMetrics.density);
        int totalSpacing = spacingInPixels * (count - 1);
        int buttonWidth = (screenWidth - totalSpacing) / count;

        for (int i = 0; i < count; i++) {
            Button button = new Button(this);
            button.setBackgroundColor(getResources().getColor(R.color.button_color));
            button.setText("" + (i + 1));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(buttonWidth, 300);

            if (i < count - 1) {
                params.setMargins(0, 0, spacingInPixels, 0);
            }

            button.setLayoutParams(params);
            buttonContainer.addView(button);
        }
    }


}