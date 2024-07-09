package com.example.fair_share;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.view.View;
import com.shawnlin.numberpicker.NumberPicker;

public class listReceipt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reciept);


    }

    public void NumberPicker(){
        NumberPicker numberPicker = findViewById(R.id.number_picker);


        Typeface typeface = Typeface.create("sans-serif-light", Typeface.NORMAL);
        numberPicker.setTypeface(typeface);

        // Using string values
        String[] data = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setValue(7);

        // OnClickListener
        numberPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        // OnValueChangeListener
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            }
        });

        // OnScrollListener
        numberPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker picker, int scrollState) {
                if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {

                }
            }
        });
    }
}