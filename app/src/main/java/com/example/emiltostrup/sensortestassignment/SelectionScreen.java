package com.example.emiltostrup.sensortestassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SelectionScreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);
    }

    /** Called when the user taps the Compass button */
    public void enterCompass(View view) {
        Intent intent = new Intent(this, Compass.class);
        startActivity(intent);
    }

    /** Called when the user taps the Accelerometer button */
    public void enterAccelerometer(View view) {
        Intent intent = new Intent(this, Accelerometer.class);
        startActivity(intent);
    }
}