package com.plguerra.f1simengineer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.plguerra.f1simengineer.DataPackets.CarSetup_Data;
import com.plguerra.f1simengineer.DataPackets.CarStatus_Data;
import com.plguerra.f1simengineer.DataPackets.Lap_Data;
import com.plguerra.f1simengineer.DataPackets.Motion_Data;
import com.plguerra.f1simengineer.DataPackets.PacketHeader;
import com.plguerra.f1simengineer.DataPackets.Session_Data;
import com.plguerra.f1simengineer.DataPackets.Telemetry_Data;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class CustomPageAdapter extends PagerAdapter {
    private int[] layouts;
    private LayoutInflater layoutInflater;
    private Context context;
    PacketHeader packet;
    CarSetup_Data carsetup_data;
    CarStatus_Data carstatus_data;
    Lap_Data lap_data;
    Motion_Data motion_data;
    Session_Data session_data;
    Telemetry_Data telemetry_data;

    public TextView data;
    public TextView gear;
    public TextView speed;
    public TextView player_position;
    public TextView laps;
    public TextView tyre_health_FL;
    public TextView tyre_health_FR;
    public TextView tyre_health_RL;
    public TextView tyre_health_RR;
    public TextView time;
    public TextView lapDelta;
    public TextView car_Ahead;
    public TextView car_Behind;



    public CustomPageAdapter(int[] layouts, Context context){
        this.layouts = layouts;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(layouts[position],container,false);
        container.addView((view));

        gear = view.findViewById(R.id.Gear);
        speed = view.findViewById(R.id.Speed);
        player_position = view.findViewById(R.id.Position);
        laps = view.findViewById(R.id.Laps);
        tyre_health_FL = view.findViewById(R.id.Tyre_Health_FL);
        tyre_health_FR = view.findViewById(R.id.Tyre_Health_FR);
        tyre_health_RL = view.findViewById(R.id.Tyre_Health_RL);
        tyre_health_RR = view.findViewById(R.id.Tyre_Health_RR);
        time = view.findViewById(R.id.Time);
//        lapDelta = view.findViewById(R.id.LapDelta);
//        car_Ahead = view.findViewById(R.id.Car_Ahead);
//        car_Behind = view.findViewById(R.id.Car_Behind);
        data = view.findViewById(R.id.data);
//        updateView();

        return view;
    }

        public void updateView() {
//                this.gear.setText("55");
//                speed.setText(String.valueOf(telemetry_data.speed));
//            player_position.setText(String.valueOf(lap_data.carPosition));
//                laps.setText(lap_data.currentLapNum + "/" + session_data.totalLaps);
//                tyre_health_RL.setText(String.valueOf(100-carstatus_data.tyresWear[0]));
//                tyre_health_RR.setText(String.valueOf(100-carstatus_data.tyresWear[1]));
//                tyre_health_FL.setText(String.valueOf(100-carstatus_data.tyresWear[2]));
//                tyre_health_FR.setText(String.valueOf(100-carstatus_data.tyresWear[3]));
//                time.setText(String.valueOf(lap_data.getCurrentLapTime(true)));
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View)object;
        container.removeView(view);

    }
}
