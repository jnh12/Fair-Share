package com.example.fair_share;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    private FloatingActionButton dropDown_button;
    private FloatingActionButton cancel_button;
    private FloatingActionButton info_button;
    private FloatingActionButton settings_button;
    private FloatingActionButton recent_button;
    private ImageButton startCapture_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialising elements
        startCapture_button = findViewById(R.id.startCapture_button);
        recent_button = findViewById(R.id.recent_button);
        cancel_button = findViewById(R.id.cancel_button);
        dropDown_button = findViewById(R.id.dropdown_button);
        info_button = findViewById(R.id.info_button);
        settings_button = findViewById(R.id.settings_button);

        //start Camera View
        startCameraFragment();

        //onClick methods
        startCapture_button.setOnClickListener(this::handleButtonClick);
        recent_button.setOnClickListener(this::handleButtonClick);
        cancel_button.setOnClickListener(this::handleButtonClick);
        dropDown_button.setOnClickListener(this::handleButtonClick);
        info_button.setOnClickListener(this::handleButtonClick);
        settings_button.setOnClickListener(this::handleButtonClick);
    }


    //ALL ONCLICK EVENTS HERE
    private void handleButtonClick(View view) {
        int id = view.getId();

        if (id == R.id.startCapture_button) {
            Intent intent = new Intent(this, takeCameraView.class);
            startActivity(intent);
        }
        else if (id == R.id.recent_button) {
            Intent intent = new Intent(this, recentsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.cancel_button) {
            dropDown_button.setVisibility(View.VISIBLE);
            cancel_button.setVisibility(View.INVISIBLE);
            settings_button.setVisibility(View.INVISIBLE);
            info_button.setVisibility(View.INVISIBLE);
        }
        else if (id == R.id.dropdown_button) {
            dropDown_button.setVisibility(View.INVISIBLE);
            cancel_button.setVisibility(View.VISIBLE);
            settings_button.setVisibility(View.VISIBLE);
            info_button.setVisibility(View.VISIBLE);
        }
        else if (id == R.id.info_button) {
            Intent intent = new Intent(this, infoActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.settings_button) {
            Intent intent = new Intent(this, settingsActivity.class);
            startActivity(intent);
        }
    }

    public void startCameraFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.camera_container, new cameraFragment());
        transaction.commit();
    }

}