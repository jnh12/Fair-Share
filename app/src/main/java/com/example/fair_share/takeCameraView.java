package com.example.fair_share;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class takeCameraView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_camera_view);

        //start Camera View
        startCameraFragment();

        //initialize buttons
        ImageButton startCapture_button = findViewById(R.id.startCapture_button);

        //initialize onClick methods
        startCapture_button.setOnClickListener(this::handleButtonClick);


    }

    public void startCameraFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.camera_container, new cameraFragment());
        transaction.commit();
    }

    //ALL ONCLICK EVENTS HERE
    private void handleButtonClick(View view) {
        int id = view.getId();

        if (id == R.id.startCapture_button) {
            Intent intent = new Intent(this, listReceipt.class);
            startActivity(intent);
        }
    }

}
