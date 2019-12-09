package com.plguerra.f1simengineer;


import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.plguerra.f1simengineer.DataPackets.TrackOverview_Data;

import java.util.ArrayList;
import java.util.List;

public class TrackOverview extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_overview);

        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        TrackOverviewAdapter trackOverviewAdapter = new TrackOverviewAdapter(createList(18));
        recList.setAdapter(trackOverviewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();
        if (id == R.id.) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    private List<TrackOverviewInfo> createList(int size) {

//        addFakeData();
        List<TrackOverviewInfo> sqldata = LoadSessionInfo();

        List<TrackOverviewInfo> result = new ArrayList<TrackOverviewInfo>();
        for (int i=0; i <= size; i++) {
            TrackOverviewInfo toi = new TrackOverviewInfo();
            TrackOverview_Data data = new TrackOverview_Data(i);
            toi.trackId = i;
            toi.trackName = data.getTrack();
            int praticeCount = 0, qualifyingCount = 0, raceCount = 0;
            for(TrackOverviewInfo item: sqldata) {
                if (item.trackName != null) {
                    if (item.trackName.equals(toi.trackName)) {
                        if (item.sessionType.equals("Practice")) {
                            praticeCount++;
                        }
                        if (item.sessionType.equals("Qualifying")) {
                            qualifyingCount++;
                        }
                        if (item.sessionType.equals("Race")) {
                            raceCount++;
                        }
                    }
                }
            }
            int sessionCount = praticeCount + qualifyingCount + raceCount;
            toi.sessionsNumber = TrackOverviewInfo.SESSION_PREFIX + sessionCount;
            toi.practiceNumber = TrackOverviewInfo.PRACTICE_PREFIX + praticeCount;
            toi.qualifyingNumber = TrackOverviewInfo.QUALIFYING_PREFIX + qualifyingCount;
            toi.raceNumber = TrackOverviewInfo.RACE_PREFIX + raceCount;
            if((i%2)== 0) {
                toi.cardColor = Color.parseColor("#1B1B1B");
            } else {
                toi.cardColor = Color.BLACK;
            }
            toi.imageResource = getResources().getIdentifier("com.plguerra.f1simengineer:drawable/" + data.getTrackImage(), null, null);

            result.add(toi);
        }

        return result;
    }

    //Load Session Information
    public List<TrackOverviewInfo> LoadSessionInfo(){

        String[] projection = {                                  // The columns to return for each row
                DataProvider.SESSION_TABLE_COL_TRACK,
                DataProvider.SESSION_TABLE_COL_SESSTYPE,
        };
        Cursor myCursor = getContentResolver().query(DataProvider.CONTENT_URI, projection, null, null, null);
        List itemIds = new ArrayList<TrackOverviewInfo>();
        while(myCursor.moveToNext()) {
            TrackOverviewInfo toi = new TrackOverviewInfo();
            int index = myCursor.getColumnIndexOrThrow("Track_Name");
            toi.trackName = myCursor.getString(index);
            index = myCursor.getColumnIndexOrThrow("Session_Type");
            toi.sessionType = myCursor.getString(index);
            itemIds.add(toi);
        }
        myCursor.close();
        return itemIds;
    }

    public void addFakeData() {
        ContentValues myCV = new ContentValues();
        myCV.put(DataProvider.SESSION_TABLE_COL_DATE, "Dec 8, 2019");
        myCV.put(DataProvider.SESSION_TABLE_COL_SESSTYPE, "Practice");
        myCV.put(DataProvider.SESSION_TABLE_COL_TRACK, "Abu Dhabi");
        myCV.put(DataProvider.SESSION_TABLE_COL_TEAM, "Mercedes");
        myCV.put(DataProvider.SESSION_TABLE_COL_TYRETYPE, "C5");
        myCV.put(DataProvider.SESSION_TABLE_COL_LAPS, "15");
        myCV.put(DataProvider.SESSION_TABLE_COL_TOPSPEED, "215");
        myCV.put(DataProvider.SESSION_TABLE_COL_AVGSPEED, "190");
        myCV.put(DataProvider.SESSION_TABLE_COL_POSITION, "2");
        myCV.put(DataProvider.SESSION_TABLE_COL_SESSTIME, "25:15.210");
        myCV.put(DataProvider.SESSION_TABLE_COL_BESTLAP, "1:28.485");
        myCV.put(DataProvider.SESSION_TABLE_COL_AVGTIME, "1:23.008");
        myCV.put(DataProvider.SESSION_TABLE_COL_BESTSECTOR1, "27.456");
        myCV.put(DataProvider.SESSION_TABLE_COL_BESTSECTOR2, "22.782");
        myCV.put(DataProvider.SESSION_TABLE_COL_BESTSECTOR3, "23.991");
        getContentResolver().insert(DataProvider.CONTENT_URI, myCV);
    }
}
