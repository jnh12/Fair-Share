package com.example.fair_share;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class takeCameraView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_camera_view);

        //start Camera View
        startCameraFragment();
    }

    public void startCameraFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.camera_container, new cameraFragment());
        transaction.commit();
    }

}
