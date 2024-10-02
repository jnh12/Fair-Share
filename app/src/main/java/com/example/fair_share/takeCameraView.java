package com.example.fair_share;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.shawnlin.numberpicker.NumberPicker;

public class takeCameraView extends AppCompatActivity {

    private int selectedValue = 4; // Default value
    private cameraFragment cameraFragmentInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_camera_view);

        //start Camera View
        startCameraFragment();

        //initialize Componets
        ImageButton startCapture_button = findViewById(R.id.startCapture_button);
        NumberPicker();

        // Initialize onClick methods
        startCapture_button.setOnClickListener(this::handleButtonClick);
    }

    public void startCameraFragment(){
        cameraFragmentInstance = new cameraFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.camera_container, cameraFragmentInstance);
        transaction.commit();
    }

    public void NumberPicker(){
        NumberPicker numberPicker = findViewById(R.id.number_picker);

        numberPicker.setMinValue(2);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(4);

        //values that change while scrolling
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                selectedValue = newVal;
            }
        });
    }

    //ALL ONCLICK EVENTS HERE
    private void handleButtonClick(View view) {
        int id = view.getId();

        if (id == R.id.startCapture_button) {
//            Intent intent = new Intent(this, listReceipt.class);
//            intent.putExtra("selectedValue", selectedValue);
//            startActivity(intent);
                cameraFragmentInstance.takePicture();
        }
    }
}
