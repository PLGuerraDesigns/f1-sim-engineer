package com.plguerra.f1simengineer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Obtain Buttons from activity_main.xml
        Button Dashboard = findViewById(R.id.dashboard_button);
        Button Analysis = findViewById(R.id.analysis_button);
        Button Setting = findViewById(R.id.settings_button);

        //Listening for Button Presses
        Dashboard.setOnClickListener(this);
        Analysis.setOnClickListener(this);
        Setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.dashboard_button:
                intent = new Intent(this, Dashboard.class);
                startActivity(intent);
                break;

            case R.id.analysis_button:
                openSimpleTrackOverview();
                break;

            case R.id.settings_button:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

        }
    }

    //Open TrackOverview page
    public void openSimpleTrackOverview(){
        Intent intent = new Intent(this, TrackOverview.class);
        startActivity(intent);
    }
}

