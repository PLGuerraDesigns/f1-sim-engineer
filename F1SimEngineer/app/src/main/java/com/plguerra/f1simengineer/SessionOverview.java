package com.plguerra.f1simengineer;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_overview);
        recyclerView = (RecyclerView) findViewById(R.id.sessionOverviewList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SessionOverviewAdapter SessionOverviewInfo = new SessionOverviewAdapter(createList(10));
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


    private List<SessionOverviewInfo> createList(int size) {

        List<SessionOverviewInfo> result = new ArrayList<SessionOverviewInfo>();
        for (int i=0; i <= size; i++) {
            SessionOverviewInfo soi = new SessionOverviewInfo();
            SessionOverview_Data data = new SessionOverview_Data(i);
            soi.sessionType = "Practice"; // Needs to get it from database
            Format f = new SimpleDateFormat("MM/dd/yy"); //Needs to be done better lol
            soi.sessionDate = f.format(new Date());
            soi.sessionVehicle = SessionOverviewInfo.VEHICLE_PREFIX + i;
            soi.sessionBestLap = SessionOverviewInfo.BESTLAPS_PREFIX + i;
            soi.sessionTire = SessionOverviewInfo.TIRE_PREFIX + i;
            soi.sessionLaps = SessionOverviewInfo.LAPS_PREFIX + i;
            soi.sessionPosition = SessionOverviewInfo.POS_PREFIX + i;
            if ((i % 2) == 0) {
                soi.backgroundColor = "grey";
            } else {
                soi.backgroundColor = "black";
            }
            result.add(soi);
        }

        return result;
    }

}
