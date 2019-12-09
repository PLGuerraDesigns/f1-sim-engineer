package com.plguerra.f1simengineer;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SessionViewActivity extends AppCompatActivity{

    String tableId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_view);

        //Get string form intent
        Bundle p = getIntent().getExtras();
        tableId = p.getString("tableId");

        setTextBoxes();
    }

    private void setTextBoxes() {
        //Call function for database
        SessionOverviewInfo soi = getDataFromDatabase();
        //Set the different textbox
        TextView trackTitleTextView = findViewById(R.id.trackTitle);
        TextView numberOfLapsTextView = findViewById(R.id.numberofLaps);
        TextView fastestLapTimeTextView = findViewById(R.id.fastestLapTime);
        TextView averageLapTimeTextView = findViewById(R.id.averageLapTime);
        TextView sector1TimeTextView = findViewById(R.id.sector1Time);
        TextView sector2TimeTextView = findViewById(R.id.sector2Time);
        TextView sector3TimeTextView = findViewById(R.id.sector3Time);
    }

    private SessionOverviewInfo getDataFromDatabase() {
        String[] projection = {                                  // The columns to return for each row
                DataProvider.PHOTO_TABLE_COL_TRACK,
                DataProvider.PHOTO_TABLE_COL_LAPS,
                DataProvider.PHOTO_TABLE_COL_BESTLAP,
                DataProvider.PHOTO_TABLE_COL_AVGTIME,
                DataProvider.PHOTO_TABLE_COL_BESTSECTOR1,
                DataProvider.PHOTO_TABLE_COL_BESTSECTOR2,
                DataProvider.PHOTO_TABLE_COL_BESTSECTOR3
        };
        String selection = DataProvider.PHOTO_TABLE_COL_ID + " =?";
        String[] selectionArg = {tableId};
        SessionOverviewInfo soi = new SessionOverviewInfo();
        Cursor myCursor = getContentResolver().query(DataProvider.CONTENT_URI, projection, selection, selectionArg, null);
        while(myCursor.moveToNext()) {
            int index = myCursor.getColumnIndexOrThrow("Track_Name");
            soi.trackTitle = myCursor.getString(index);
            index = myCursor.getColumnIndexOrThrow("Total_Laps");
            soi.sessionLaps = myCursor.getString(index);
            index = myCursor.getColumnIndexOrThrow("Best_Lap_Time");
            soi.sessionBestLap = myCursor.getString(index);
            index = myCursor.getColumnIndexOrThrow("Average_Lap_Time");
            soi.averageLap = myCursor.getString(index);
            index = myCursor.getColumnIndexOrThrow("Best_Sector_1_Time");
            soi.sessionTire = myCursor.getString(index);
            index = myCursor.getColumnIndexOrThrow("Best_Sector_2_Time");
            soi.sessionTire = myCursor.getString(index);
            index = myCursor.getColumnIndexOrThrow("Best_Sector_3_Time");
            soi.sessionType = myCursor.getString(index);
        }
        myCursor.close();
        return soi;
    }
    }
}
