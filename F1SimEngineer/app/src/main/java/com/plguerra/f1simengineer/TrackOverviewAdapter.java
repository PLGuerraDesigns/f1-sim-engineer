package com.plguerra.f1simengineer;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrackOverviewAdapter extends RecyclerView.Adapter<TrackOverviewAdapter.TrackOverviewHolder> {

    private List<TrackOverviewInfo> trackOverviewList;

    public TrackOverviewAdapter(List<TrackOverviewInfo> trackOverviewList) {
        this.trackOverviewList = trackOverviewList;
    }

    @Override
    public int getItemCount() {
        return trackOverviewList.size();
    }

    @Override
    public void onBindViewHolder(TrackOverviewHolder trackOverviewHolder, int i) {
        TrackOverviewInfo toi = trackOverviewList.get(i);
        trackOverviewHolder.vTrackName.setText(toi.trackName);
        trackOverviewHolder.vSessionNumber.setText(toi.sessionsNumber);
        trackOverviewHolder.vPracticeNumber.setText(toi.practiceNumber);
        trackOverviewHolder.vQualifyingNumber.setText(toi.qualifyingNumber);
        trackOverviewHolder.vRaceNumber.setText(toi.raceNumber);
    }

    @Override
    public TrackOverviewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.track_card, viewGroup, false);

        return new TrackOverviewHolder(itemView);
    }

    public static class TrackOverviewHolder extends RecyclerView.ViewHolder {

        protected TextView vTrackName;
        protected TextView vSessionNumber;
        protected TextView vPracticeNumber;
        protected TextView vQualifyingNumber;
        protected TextView vRaceNumber;

        public TrackOverviewHolder(View v) {
            super(v);
            vTrackName =  (TextView) v.findViewById(R.id.trackName);
            vSessionNumber = (TextView)  v.findViewById(R.id.sessionNumber);
            vPracticeNumber = (TextView)  v.findViewById(R.id.practiceNumber);
            vQualifyingNumber = (TextView) v.findViewById(R.id.qualifyingNumber);
            vRaceNumber = (TextView) v.findViewById(R.id.raceNumber);
        }
    }
}
