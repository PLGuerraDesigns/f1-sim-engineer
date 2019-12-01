package com.plguerra.f1simengineer;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.plguerra.f1simengineer.DataPackets.CarSetup_Data;
import com.plguerra.f1simengineer.DataPackets.CarStatus_Data;
import com.plguerra.f1simengineer.DataPackets.Event_Data;
import com.plguerra.f1simengineer.DataPackets.Lap_Data;
import com.plguerra.f1simengineer.DataPackets.Motion_Data;
import com.plguerra.f1simengineer.DataPackets.PacketHeader;
import com.plguerra.f1simengineer.DataPackets.Session_Data;
import com.plguerra.f1simengineer.DataPackets.Telemetry_Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class Dashboard extends AppCompatActivity{
//    private ViewPager viewPager;
//    private int[] layouts = {R.layout.dash_f1,R.layout.dash_simple};
//    private  CustomPageAdapter customPageAdapter;

    public TextView gear;
    public TextView speed;
    public TextView position;
    public TextView laps;
    public TextView tyre_health_FL;
    public TextView tyre_health_FR;
    public TextView tyre_health_RL;
    public TextView tyre_health_RR;
    public TextView time;
    public TextView fuel_remaining;
    public TextView best_Lap;
    public TextView last_Lap;
    public ImageView drs;
    public ImageView weather;
    public ImageView tyre_compound;
    LinearLayout limiter;

    boolean carSetup_Received = false;
    boolean carStatus_Received = false;
    boolean event_Received = false;
    boolean lap_Received = false;
    boolean motion_Received = false;
    boolean session_Received = false;
    boolean telemetry_Received = false;
    boolean data_Received = false;
    int topSpeed = 0;
    int speedSum = 0;
    int speedCount = 0;
    float BestS1 = 0;
    float BestS2 = 0;
    float BestS3 = 0;
    float totalLapTime = 0;
    int lastLapNum = 1;
    public static int MAX_BUFFER = 2048;
    private static final String TAG = "Dashboard";
    PacketHeader packetheader;
    CarSetup_Data carsetup_data;
    CarStatus_Data carstatus_data;
    final Handler handler = new Handler();
    Lap_Data lap_data;
    Motion_Data motion_data;
    public Boolean running;
    Session_Data session_data;
    Telemetry_Data telemetry_data;
    Event_Data event_Data;



    public void onStop() {
        super.onStop();
        running = Boolean.valueOf(false);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_menu_save)
                .setTitle("Save Session?")
                .setMessage("You will not be able to continue the same session.")
                .setPositiveButton("Save", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddSessionData();
                        finish();
                    }

                })
                .setNegativeButton("Cancel", null)
                .show();
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_f1);
//        viewPager = findViewById(R.id.viewpager);
//        customPageAdapter = new CustomPageAdapter(layouts,this);
//        viewPager.setAdapter(customPageAdapter);


//        getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Container, new SimpleDash()).commit();
//        SimpleDash.updateSimpleFragment("5");

        gear = findViewById(R.id.Gear);
        speed = findViewById(R.id.Speed);
        position = findViewById(R.id.Position);
        laps = findViewById(R.id.Laps);
        tyre_health_FL = findViewById(R.id.Tyre_Health_FL);
        tyre_health_FR = findViewById(R.id.Tyre_Health_FR);
        tyre_health_RL = findViewById(R.id.Tyre_Health_RL);
        tyre_health_RR = findViewById(R.id.Tyre_Health_RR);
        time = findViewById(R.id.Time);
        fuel_remaining = findViewById(R.id.Fuel_Remaining);
        best_Lap = findViewById(R.id.Best_Lap);
        last_Lap = findViewById(R.id.Last_Lap);
        limiter = findViewById(R.id.Limiter);
        drs = findViewById(R.id.DRS);
        weather = findViewById(R.id.Weather);
        tyre_compound = findViewById(R.id.Tyre);


        startServerSocket();
        running = Boolean.valueOf(true);
    }

    private void startServerSocket() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try{
                    DatagramSocket datagramSocket = new DatagramSocket(20777);
                    byte[] msg = new byte[Dashboard.MAX_BUFFER];
                    DatagramPacket datagramPacket = new DatagramPacket(msg, msg.length);


                while (running.booleanValue()) {
                    datagramSocket.receive(datagramPacket);

                        packetheader = new PacketHeader(msg);
                        UnpackData(packetheader.packetId, msg);
//                        updateView();

//                        updateUI(packet.toString());
                }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();
    }

    public void AddSessionData() {
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        ContentValues myCV = new ContentValues();


        if(data_Received) {
            myCV.put(DataProvider.PHOTO_TABLE_COL_DATE, currentDate);
            myCV.put(DataProvider.PHOTO_TABLE_COL_TEAM, "Mercedes"); // Add participants

            if (session_Received) {
                myCV.put(DataProvider.PHOTO_TABLE_COL_SESSTYPE, session_data.getSessionType());
                myCV.put(DataProvider.PHOTO_TABLE_COL_TRACK, session_data.getTrack());
                myCV.put(DataProvider.PHOTO_TABLE_COL_SESSTIME, session_data.getSessionDuration());
            }
            if (carStatus_Received) {
                myCV.put(DataProvider.PHOTO_TABLE_COL_TYRETYPE, carstatus_data.getTyreCompound());
            }
            if (lap_Received) {
                myCV.put(DataProvider.PHOTO_TABLE_COL_LAPS, lap_data.currentLapNum);
                myCV.put(DataProvider.PHOTO_TABLE_COL_POSITION, lap_data.carPosition);
                myCV.put(DataProvider.PHOTO_TABLE_COL_BESTLAP, lap_data.getBestLapTime(true));
                myCV.put(DataProvider.PHOTO_TABLE_COL_AVGTIME, (totalLapTime / (lap_data.currentLapNum - 1)));
                myCV.put(DataProvider.PHOTO_TABLE_COL_BESTSECTOR1, BestS1);
                myCV.put(DataProvider.PHOTO_TABLE_COL_BESTSECTOR2, BestS2);
                myCV.put(DataProvider.PHOTO_TABLE_COL_BESTSECTOR3, BestS3);
            }
            if(telemetry_Received){
                myCV.put(DataProvider.PHOTO_TABLE_COL_TOPSPEED, topSpeed);
                myCV.put(DataProvider.PHOTO_TABLE_COL_AVGSPEED, (speedSum/speedCount));
            }

            getContentResolver().insert(DataProvider.CONTENT_URI, myCV);
//            Toast.makeText(this, "Session Saved.", Toast.LENGTH_SHORT).show();

        }
//        else{
//            Toast.makeText(this, "No Session Data.", Toast.LENGTH_SHORT).show();
//        }
    }

    private void updateView() {
        handler.post(new Runnable() {
            @SuppressLint("SetTextI18n")
            public void run() {
                try {
                    gear.setText(telemetry_data.getGear());
                    speed.setText(String.valueOf(telemetry_data.speed));
                    position.setText(String.valueOf(lap_data.carPosition));
                    if(session_data.totalLaps > 1) {
                        laps.setText(lap_data.currentLapNum + "/" + session_data.totalLaps);
                    }
                    else{
                        laps.setText(String.valueOf(lap_data.currentLapNum));
                    }
                    tyre_health_RL.setText(String.valueOf(100-carstatus_data.tyresWear[0]));
                    tyre_health_RR.setText(String.valueOf(100-carstatus_data.tyresWear[1]));
                    tyre_health_FL.setText(String.valueOf(100-carstatus_data.tyresWear[2]));
                    tyre_health_FR.setText(String.valueOf(100-carstatus_data.tyresWear[3]));
                    time.setText(String.valueOf(lap_data.getCurrentLapTime(true)));
                    best_Lap.setText(String.valueOf(lap_data.getBestLapTime(true)));
                    last_Lap.setText(String.valueOf(lap_data.getLastLapTime(true)));
//                    last_Lap.setText(String.valueOf(packet.playerCarIndex));
//
                    switch(carstatus_data.actualTyreCompound){
                        case 16:
                        case 11:
                            tyre_compound.setImageResource(R.drawable.c5);
                            break;
                        case 17:
                        case 12:
                            tyre_compound.setImageResource(R.drawable.c4);
                            break;
                        case 18:
                        case 13:
                            tyre_compound.setImageResource(R.drawable.c3);
                        case 19:
                        case 14:
                        case 9:
                            tyre_compound.setImageResource(R.drawable.c2);
                        case 20:
                            tyre_compound.setImageResource(R.drawable.c1);
                            break;
                        case 15:
                        case 10:
                        case 8:
                            tyre_compound.setImageResource(R.drawable.wet);
                            break;
                        case 7:
                            tyre_compound.setImageResource(R.drawable.inter);
                            break;
                        default:
                            tyre_compound.setImageResource(0);
                            break;
                    }

                    switch(session_data.weather){
                            case 0:
                                weather.setImageResource(R.drawable.clear);
                                break;
                            case 1:
                                weather.setImageResource(R.drawable.light_cloud);
                                break;
                            case 2:
                                weather.setImageResource(R.drawable.overcast);
                                break;
                            case 3:
                                weather.setImageResource(R.drawable.light_rain);
                                break;
                            case 4:
                                weather.setImageResource(R.drawable.heavy_rain);
                                break;
                            case 5:
                                weather.setImageResource(R.drawable.storm);
                                break;
                            default:
                                weather.setImageResource(0);
                                break;
                    }

                    if(telemetry_data.drs == 1){
                        drs.setImageResource(R.drawable.drs_on);
                    }
                    else{
                        drs.setImageResource(R.drawable.drs_off);
                    }

                    String fuel_formatted = String.format("%.2f", carstatus_data.fuelRemainingLaps);
                    fuel_remaining.setText("Fuel Remaining: " + fuel_formatted + " Laps");
                    if(carstatus_data.fuelRemainingLaps < 4){
                        fuel_remaining.setTextColor(getResources().getColor(R.color.red,getResources().newTheme()));
                    }
                    else{
                        fuel_remaining.setTextColor(getResources().getColor(R.color.white,getResources().newTheme()));
                    }

                    if(telemetry_data.revLightsPercent > 60){
                        gear.setTextColor(getResources().getColor(R.color.white,getResources().newTheme()));
                        limiter.setBackgroundColor(getResources().getColor(R.color.red,getResources().newTheme()));
                    }
                    else{
                        gear.setTextColor(getResources().getColor(R.color.red,getResources().newTheme()));
                        limiter.setBackgroundColor(getResources().getColor(R.color.black,getResources().newTheme()));
                    }

                    if(telemetry_data.speed > topSpeed){
                        topSpeed = telemetry_data.speed;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public void UnpackData(short packetId, byte[] packet) {
        data_Received = true;

        switch (packetId) {
            case 0:
                motion_data = new Motion_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
                motion_Received = true;
                break;
            case 1:
                session_data = new Session_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
                session_Received = true;
                break;
            case 2:
                lap_data = new Lap_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
                lap_Received = true;
                try {
                    if (BestS1 > Float.valueOf(lap_data.getSector1Time(true))) {
                        BestS1 = Float.valueOf(lap_data.getSector1Time(true));
                    }
                    if (BestS2 > Float.valueOf(lap_data.getSector2Time(true))) {
                        BestS2 = Float.valueOf(lap_data.getSector2Time(true));
                    }
                    if (BestS3 > Float.valueOf(lap_data.getSector3Time(true))) {
                        BestS3 = Float.valueOf(lap_data.getSector3Time(true));
                    }
                    if (lap_data.currentLapNum > lastLapNum) {
                        totalLapTime = lap_data.lastLapTime;
                        lastLapNum = lap_data.currentLapNum;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
            case 3:
                event_Data = new Event_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
                event_Received = true;
                if(event_Data.getEventString().equals("SEND")){
                    AddSessionData();
                }
                break;
            case 4:
                //Might not be used
                break;
            case 5:
                carsetup_data = new CarSetup_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
                carSetup_Received = true;
                break;
            case 6:
                telemetry_data = new Telemetry_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
                telemetry_Received = true;
                speedSum += telemetry_data.speed;
                speedCount++;
                updateView();
                break;
            case 7:
                carstatus_data = new CarStatus_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
                carStatus_Received = true;
                break;
            default:
                break;
        }
    }
}
