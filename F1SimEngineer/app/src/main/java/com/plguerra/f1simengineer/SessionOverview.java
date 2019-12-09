package com.plguerra.f1simengineer;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.plguerra.f1simengineer.DataPackets.SessionOverview_Data;
import com.plguerra.f1simengineer.DataPackets.TrackOverview_Data;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SessionOverview extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    int sqlCount = 0;
    String trackNameFromIntent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_overview);
        recyclerView = (RecyclerView) findViewById(R.id.sessionOverviewList);
        //Get string form intent
        Bundle p = getIntent().getExtras();
        trackNameFromIntent = p.getString("TrackName");

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SessionOverviewAdapter SessionOverviewInfo = new SessionOverviewAdapter(createList());
        recyclerView.setAdapter(SessionOverviewInfo);

        //Spinners
        Spinner filterSpinner = (Spinner) findViewById(R.id.filter_spinner);
        Spinner sortSpinner = (Spinner) findViewById(R.id.sort_spinner);



        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterFilter = ArrayAdapter.createFromResource(this,
                R.array.filter_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        filterSpinner.setAdapter(adapterFilter);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSort = ArrayAdapter.createFromResource(this,
                R.array.sort_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sortSpinner.setAdapter(adapterSort);
    }


    private List<SessionOverviewInfo> createList() {

        List<SessionOverviewInfo> result = new ArrayList<SessionOverviewInfo>();
        List<SessionOverviewInfo> sqlInfo = LoadTask();
        int i = 0;
        for (SessionOverviewInfo item : sqlInfo) {
            SessionOverviewInfo soi = new SessionOverviewInfo();
            soi.sessionType = item.sessionType; // Needs to get it from database
            soi.sessionDate = item.sessionDate;
            soi.sessionVehicle = SessionOverviewInfo.VEHICLE_PREFIX + item.sessionVehicle;
            soi.sessionBestLap = SessionOverviewInfo.BESTLAPS_PREFIX + item.sessionBestLap;
            soi.sessionTire = SessionOverviewInfo.TIRE_PREFIX + item.sessionTire;
            soi.sessionLaps = SessionOverviewInfo.LAPS_PREFIX + item.sessionLaps;
            soi.sessionPosition = SessionOverviewInfo.POS_PREFIX + item.sessionPosition;
            if ((i % 2) == 0) {
                soi.backgroundColor = Color.parseColor("#1B1B1B");
            } else {
                soi.backgroundColor = Color.BLACK;
            }
            i++;
            result.add(soi);
        }

        return result;
    }

    //Load Task Information Based on ID
    public List<SessionOverviewInfo> LoadTask(){

        String[] projection = {                                  // The columns to return for each row
                DataProvider.PHOTO_TABLE_COL_BESTLAP,
                DataProvider.PHOTO_TABLE_COL_DATE,
                DataProvider.PHOTO_TABLE_COL_LAPS,
                DataProvider.PHOTO_TABLE_COL_POSITION,
                DataProvider.PHOTO_TABLE_COL_TEAM,
                DataProvider.PHOTO_TABLE_COL_TYRETYPE,
                DataProvider.PHOTO_TABLE_COL_SESSTYPE,
                DataProvider.PHOTO_TABLE_COL_ID
        };
        String selection = DataProvider.PHOTO_TABLE_COL_TRACK + " =?";
        String[] selectionArg = {trackNameFromIntent};
        Cursor myCursor = getContentResolver().query(DataProvider.CONTENT_URI, projection, selection, selectionArg, null);
        List itemIds = new ArrayList<TrackOverviewInfo>();
        while(myCursor.moveToNext()) {
            SessionOverviewInfo soi = new SessionOverviewInfo();
            int index = myCursor.getColumnIndexOrThrow("Date");
            soi.sessionDate = myCursor.getString(index);
            index = myCursor.getColumnIndexOrThrow("Total_Laps");
            soi.sessionLaps = myCursor.getString(index);
            index = myCursor.getColumnIndexOrThrow("Final_Position");
            soi.sessionPosition = myCursor.getString(index);
            index = myCursor.getColumnIndexOrThrow("Team_Car");
            soi.sessionVehicle = myCursor.getString(index);
            index = myCursor.getColumnIndexOrThrow("Best_Lap_Time");
            soi.sessionBestLap = myCursor.getString(index);
            index = myCursor.getColumnIndexOrThrow("Tyre_Compound");
            soi.sessionTire = myCursor.getString(index);
            index = myCursor.getColumnIndexOrThrow("Session_Type");
            soi.sessionType = myCursor.getString(index);
            index = myCursor.getColumnIndexOrThrow("_ID");
            soi.sessionId = myCursor.getString(index);
            itemIds.add(soi);
        }
        myCursor.close();
        return itemIds;
    }



}
