package com.example.fair_share;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.shawnlin.numberpicker.NumberPicker;

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

        //start number picker
        NumberPicker();
    }

    public void NumberPicker(){
        NumberPicker numberPicker = findViewById(R.id.number_picker);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(4);

        //values change while scrolling
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                updateButtons(newVal);
            }
        });

        updateButtons(numberPicker.getValue());
    }

    private void updateButtons(int count) {
        buttonContainer.removeAllViews();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int buttonWidth = screenWidth / count;

        for (int i = 0; i < count; i++) {
            Button button = new Button(this);
            button.setText("Button " + (i + 1));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(buttonWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
            button.setLayoutParams(params);
            buttonContainer.addView(button);
        }
    }
}