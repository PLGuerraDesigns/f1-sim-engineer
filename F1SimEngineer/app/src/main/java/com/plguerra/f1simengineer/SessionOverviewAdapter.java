package com.plguerra.f1simengineer;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SessionOverviewAdapter extends RecyclerView.Adapter<SessionOverviewAdapter.SessionOverviewHolder> {

    private List<SessionOverviewInfo> sessionOverviewList;

    public SessionOverviewAdapter(List<SessionOverviewInfo> sessionOverviewList) {
        this.sessionOverviewList = sessionOverviewList;
    }

    @Override
    public int getItemCount() {
        return sessionOverviewList.size();
    }

    @Override
    public void onBindViewHolder(SessionOverviewAdapter.SessionOverviewHolder sessionOverviewHolder, int i) {
        SessionOverviewInfo soi = sessionOverviewList.get(i);
        sessionOverviewHolder.vSessionType.setText(soi.sessionType);
        sessionOverviewHolder.vSessionVehicle.setText(soi.sessionVehicle);
        sessionOverviewHolder.vSessionDate.setText(soi.sessionDate);
        sessionOverviewHolder.vSessionBestLap.setText(soi.sessionBestLap);
        sessionOverviewHolder.vSessionLaps.setText(soi.sessionLaps);
        sessionOverviewHolder.vSessionPosition.setText(soi.sessionPosition);
        sessionOverviewHolder.vSessionTire.setText(soi.sessionTire);
        if(soi.backgroundColor == "black") {
            sessionOverviewHolder.vLinearLayout.setBackgroundColor(Color.BLACK);
        }
    }

    @Override
    public SessionOverviewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.session_overview_text, viewGroup, false);

        return new SessionOverviewAdapter.SessionOverviewHolder(itemView);
    }

    public static class SessionOverviewHolder extends RecyclerView.ViewHolder {
        protected TextView vSessionType;
        protected TextView vSessionVehicle;
        protected TextView vSessionDate;
        protected TextView vSessionBestLap;
        protected TextView vSessionLaps;
        protected TextView vSessionPosition;
        protected TextView vSessionTire;
        protected LinearLayout vLinearLayout;

        public SessionOverviewHolder(View v) {
            super(v);
            vSessionType =  (TextView) v.findViewById(R.id.sessionType);
            vSessionVehicle = (TextView)  v.findViewById(R.id.sessionVehicle);
            vSessionDate = (TextView)  v.findViewById(R.id.sessionDate);
            vSessionBestLap = (TextView) v.findViewById(R.id.sessionBestLap);
            vSessionLaps = (TextView) v.findViewById(R.id.sessionLaps);
            vSessionPosition = (TextView) v.findViewById(R.id.sessionPos);
            vSessionTire = (TextView) v.findViewById(R.id.sessionTire);
            vLinearLayout = (LinearLayout) v.findViewById(R.id.sessionOverviewLinearLayout);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SessionViewActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
