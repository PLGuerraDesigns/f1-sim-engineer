package com.plguerra.f1simengineer;


import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.plguerra.f1simengineer.DataPackets.Session_Data;
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

        TrackOverviewAdapter trackOverviewAdapter = new TrackOverviewAdapter(createList(5));
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

        List<TrackOverviewInfo> result = new ArrayList<TrackOverviewInfo>();
        for (int i=0; i <= size; i++) {
            TrackOverviewInfo toi = new TrackOverviewInfo();
            TrackOverview_Data data = new TrackOverview_Data(i);
            toi.trackId = i;
            toi.trackName = data.getTrack();
            toi.sessionsNumber = TrackOverviewInfo.SESSION_PREFIX + i;
            toi.practiceNumber = TrackOverviewInfo.PRACTICE_PREFIX + i;
            toi.qualifyingNumber = TrackOverviewInfo.QUALIFYING_PREFIX + i;
            toi.raceNumber = TrackOverviewInfo.RACE_PREFIX + i;
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
}
