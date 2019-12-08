package com.plguerra.f1simengineer;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SessionViewActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_view);


        getData();
    }

    private void getData() {
        //Call function for database
        //Set the different textbox
        TextView trackTitleTextView = findViewById(R.id.trackTitle);
        TextView numberOfLapsTextView = findViewById(R.id.numberofLaps);
        TextView fastestLapTimeTextView = findViewById(R.id.fastestLapTime);
        TextView averageLapTimeTextView = findViewById(R.id.averageLapTime);
        TextView sector1TimeTextView = findViewById(R.id.sector1Time);
        TextView sector2TimeTextView = findViewById(R.id.sector2Time);
        TextView sector3TimeTextView = findViewById(R.id.sector3Time);
    }
}
