package com.plguerra.f1simengineer;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SessionViewActivity extends AppCompatActivity{
    String SessionID;
    String tableId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_view);

        getintent();
        setTextBoxes();
    }

    private void setTextBoxes() {
        //Set the different textbox
        SessionOverviewInfo sessionData = LoadSession();
        TextView trackTitleTextView = findViewById(R.id.trackTitle);
        ImageView mapImage = findViewById(R.id.sessionTrackImage);
        TextView sessionType = findViewById(R.id.sessionType);
        TextView date = findViewById(R.id.date);
        TextView finalPosition = findViewById(R.id.position);
        TextView formula = findViewById(R.id.formula);
        TextView teamCar = findViewById(R.id.teamCar);
        TextView tyreCompound = findViewById(R.id.tyreCompound);
        TextView numberOfLapsTextView = findViewById(R.id.numberofLaps);
        TextView fastestLapTimeTextView = findViewById(R.id.fastestLapTime);
        TextView topSpeed = findViewById(R.id.topSpeed);
        TextView sector1TimeTextView = findViewById(R.id.sector1Time);
        TextView sector2TimeTextView = findViewById(R.id.sector2Time);
        TextView sector3TimeTextView = findViewById(R.id.sector3Time);

        //Set Image
        String temp = sessionData.trackName;
        if(temp != null && !temp.isEmpty())
        {
            temp = temp.replaceAll("[^A-Za-z]+", "").toLowerCase();
            temp = "track_" + temp;
            mapImage.setImageResource(getResources().getIdentifier("com.plguerra.f1simengineer:drawable/" + temp, null, null));
        }

        //Set Textboxes
        trackTitleTextView.setText(sessionData.trackName);
        sessionType.setText("Session Type: " + sessionData.sessionType);
        date.setText("Date: " + sessionData.sessionDate);
        finalPosition.setText("Final Position: " + sessionData.sessionPosition);
        formula.setText("Formula Type: F1");
        teamCar.setText("Team Car: " + sessionData.sessionVehicle);
        tyreCompound.setText("Tyre Compound: " + sessionData.sessionTire);
        numberOfLapsTextView.setText("Number of Laps: " + sessionData.sessionLaps);
        fastestLapTimeTextView.setText("Fastest Lap Time: " + sessionData.sessionBestLap);
        topSpeed.setText("Top Speed: " + sessionData.topSpeed + "KPH");
        if (sessionData.bestSector1.length() != 0 && sessionData.bestSector2.length() != 0 && sessionData.bestSector3.length() != 0){
            sector1TimeTextView.setText(String.format("Best Sector 1: %.3f", Double.valueOf(sessionData.bestSector1)));
            sector2TimeTextView.setText(String.format("Best Sector 2: %.3f", Double.valueOf(sessionData.bestSector2)));
            sector3TimeTextView.setText(String.format("Best Sector 3: %.3f", Double.valueOf(sessionData.bestSector3)));
        }
    }

    //Get Data from previous activity
    private void getintent() {
        if (getIntent().hasExtra("SessionID")) {
            SessionID = getIntent().getStringExtra("SessionID");
//            Log.d("ID", SessionID);
        }
    }



    //Load Task Information Based on ID
    public SessionOverviewInfo LoadSession(){

        Cursor myCursor = getContentResolver().query(DataProvider.CONTENT_URI, null, DataProvider.SESSION_TABLE_COL_ID + " = " + SessionID, null, null);
        SessionOverviewInfo soi = new SessionOverviewInfo();

        myCursor.moveToFirst();
        int index = myCursor.getColumnIndexOrThrow("Date");
        soi.sessionDate = myCursor.getString(index);
        index = myCursor.getColumnIndexOrThrow("Session_Type");
        soi.sessionType = myCursor.getString(index);
        index = myCursor.getColumnIndexOrThrow("Track_Name");
        soi.trackName = myCursor.getString(index);
        index = myCursor.getColumnIndexOrThrow("Team_Car");
        soi.sessionVehicle = myCursor.getString(index);
        index = myCursor.getColumnIndexOrThrow("Tyre_Compound");
        soi.sessionTire = myCursor.getString(index);
        index = myCursor.getColumnIndexOrThrow("Top_Speed");
        soi.topSpeed = myCursor.getString(index);
        index = myCursor.getColumnIndexOrThrow("Total_Laps");
        soi.sessionLaps = myCursor.getString(index);
        index = myCursor.getColumnIndexOrThrow("Final_Position");
        soi.sessionPosition = myCursor.getString(index);
        index = myCursor.getColumnIndexOrThrow("Final_Position");
        soi.sessionPosition = myCursor.getString(index);
        index = myCursor.getColumnIndexOrThrow("Best_Lap_Time");
        soi.sessionBestLap = myCursor.getString(index);
        index = myCursor.getColumnIndexOrThrow("Best_Sector_1_Time");
        soi.bestSector1 = myCursor.getString(index);
        index = myCursor.getColumnIndexOrThrow("Best_Sector_2_Time");
        soi.bestSector2 = myCursor.getString(index);
        index = myCursor.getColumnIndexOrThrow("Best_Sector_3_Time");
        soi.bestSector3 = myCursor.getString(index);
        myCursor.close();
        return soi;
    }
}
