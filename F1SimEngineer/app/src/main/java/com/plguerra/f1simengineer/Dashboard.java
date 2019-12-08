package com.plguerra.f1simengineer;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.plguerra.f1simengineer.DataPackets.CarStatus_Packet;
import com.plguerra.f1simengineer.DataPackets.Event_Data;
import com.plguerra.f1simengineer.DataPackets.Lap_Packet;
import com.plguerra.f1simengineer.DataPackets.PacketHeader;
import com.plguerra.f1simengineer.DataPackets.Participants_Packet;
import com.plguerra.f1simengineer.DataPackets.Session_Data;
import com.plguerra.f1simengineer.DataPackets.Telemetry_Packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class Dashboard extends AppCompatActivity{
    private static final String TAG = "Dashboard";

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

    boolean carStatus_Received = false;
    boolean event_Received = false;
    boolean lap_Received = false;
    boolean session_Received = false;
    boolean telemetry_Received = false;
    boolean data_Received = false;
    boolean participants_Received = false;
    public boolean running;
    int port = 20777;
    int topSpeed = 0;
    int speedSum = 0;
    int speedCount = 0;
    float BestS1 = 9999;
    float BestS2 = 9999;
    float BestS3 = 9999;
//    float totalLapTime = 0;
//    int lastLapNum = 1;
    int playerID = 0;
    public static int MAX_BUFFER = 2048;

    PacketHeader packetheader;
    CarStatus_Packet carStatus_packet;
    Session_Data session_data;
    Event_Data event_Data;
    Lap_Packet lap_packet;
    Telemetry_Packet telemetry_packet;
    Participants_Packet participants_packet;

    final Handler handler = new Handler();



    public void onStop() {
        super.onStop();
        running = false;
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
                .setNeutralButton("Do not Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_f1);
        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);

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

        port = Integer.valueOf(sharedPref.getString("PortInfo", "20777"));
        startServerSocket();
        running = true;
    }

    private void startServerSocket() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try{
                    DatagramSocket datagramSocket = new DatagramSocket(port);
                    byte[] msg = new byte[Dashboard.MAX_BUFFER];
                    DatagramPacket datagramPacket = new DatagramPacket(msg, msg.length);


                while (running) {
                    datagramSocket.receive(datagramPacket);

                    packetheader = new PacketHeader(msg);
                    playerID = packetheader.playerCarIndex;
                    UnpackData(packetheader.packetId, msg);
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
            if (participants_Received) {
                myCV.put(DataProvider.PHOTO_TABLE_COL_TEAM, participants_packet.ParticipantDataList.get(playerID).getTeam());
            }

            if (session_Received) {
                myCV.put(DataProvider.PHOTO_TABLE_COL_SESSTYPE, session_data.getSessionType());
                myCV.put(DataProvider.PHOTO_TABLE_COL_TRACK, session_data.getTrack());
                myCV.put(DataProvider.PHOTO_TABLE_COL_SESSTIME, session_data.getSessionDuration());
            }
            if (carStatus_Received) {
                myCV.put(DataProvider.PHOTO_TABLE_COL_TYRETYPE, carStatus_packet.CarStatusList.get(playerID).getTyreCompound());
            }
            if (lap_Received) {
                myCV.put(DataProvider.PHOTO_TABLE_COL_LAPS, lap_packet.LapDataList.get(playerID).currentLapNum);
                myCV.put(DataProvider.PHOTO_TABLE_COL_POSITION, lap_packet.LapDataList.get(playerID).carPosition);
                myCV.put(DataProvider.PHOTO_TABLE_COL_BESTLAP, lap_packet.LapDataList.get(playerID).getBestLapTime(true));
//                myCV.put(DataProvider.PHOTO_TABLE_COL_AVGTIME, (totalLapTime / (lap_packet.LapDataList.get(playerID).currentLapNum - 1)));
                if(BestS1 != 9999) {
                    myCV.put(DataProvider.PHOTO_TABLE_COL_BESTSECTOR1, BestS1);
                    myCV.put(DataProvider.PHOTO_TABLE_COL_BESTSECTOR2, BestS2);
                    myCV.put(DataProvider.PHOTO_TABLE_COL_BESTSECTOR3, BestS3);
                }
            }
            if(telemetry_Received){
                myCV.put(DataProvider.PHOTO_TABLE_COL_TOPSPEED, topSpeed);
                myCV.put(DataProvider.PHOTO_TABLE_COL_AVGSPEED, (speedSum/speedCount));
            }

            getContentResolver().insert(DataProvider.CONTENT_URI, myCV);
            showToast("Session Saved.");
            carStatus_Received = false;
            event_Received = false;
            lap_Received = false;
            session_Received = false;
            telemetry_Received = false;
            data_Received = false;
            participants_Received = false;

        }
        else{
            showToast("No Session Data.");
        }
    }

    public void showToast(final String message)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Dashboard.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateView() {
        handler.post(new Runnable() {
            public void run() {
                try {
//                    Log.d(TAG, "ID:" + packetheader.packetId);

                    gear.setText(String.valueOf(telemetry_packet.TelemetryDataList.get(playerID).getGear()));
                    speed.setText(telemetry_packet.TelemetryDataList.get(playerID).speed + "KPH");
                    position.setText(String.valueOf(lap_packet.LapDataList.get(playerID).carPosition));
                    if(session_data.totalLaps > 1) {
                        laps.setText(lap_packet.LapDataList.get(playerID).currentLapNum + "/" + session_data.totalLaps);
                    }
                    else{
                        laps.setText(String.valueOf(lap_packet.LapDataList.get(playerID).currentLapNum));
                    }
                    tyre_health_RL.setText(String.valueOf(100-carStatus_packet.CarStatusList.get(playerID).tyresWear[0]));
                    tyre_health_RR.setText(String.valueOf(100-carStatus_packet.CarStatusList.get(playerID).tyresWear[1]));
                    tyre_health_FL.setText(String.valueOf(100-carStatus_packet.CarStatusList.get(playerID).tyresWear[2]));
                    tyre_health_FR.setText(String.valueOf(100-carStatus_packet.CarStatusList.get(playerID).tyresWear[3]));
                    time.setText(String.valueOf(lap_packet.LapDataList.get(playerID).getCurrentLapTime(true)));
                    best_Lap.setText(String.valueOf(lap_packet.LapDataList.get(playerID).getBestLapTime(true)));
                    last_Lap.setText(String.valueOf(lap_packet.LapDataList.get(playerID).getLastLapTime(true)));

                    Log.d(TAG, String.valueOf(carStatus_packet.CarStatusList.get(playerID).actualTyreCompound));
                    switch(carStatus_packet.CarStatusList.get(playerID).actualTyreCompound){
//                        case 16:
                        case 11:
                            tyre_compound.setImageResource(R.drawable.c5);
                            break;
//                        case 17:
                        case 16:
                        case 12:
                            tyre_compound.setImageResource(R.drawable.c4);
                            break;
//                        case 18:
                        case 17:
                            tyre_compound.setImageResource(R.drawable.c3);
                            break;
                        case 19:
                        case 18:
                        case 14:
                        case 9:
                            tyre_compound.setImageResource(R.drawable.c2);
                            break;
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

                    if(telemetry_packet.TelemetryDataList.get(playerID).drs == 1){
                        drs.setImageResource(R.drawable.drs_on);
                    }
                    else{
                        drs.setImageResource(R.drawable.drs_off);
                    }

                    String fuel_formatted = String.format("%.2f", carStatus_packet.CarStatusList.get(playerID).fuelInTank)
                            +"/"+String.format("%.2f", carStatus_packet.CarStatusList.get(playerID).fuelCapacity);
                    fuel_remaining.setText("Fuel: " + fuel_formatted);

                    if(telemetry_packet.TelemetryDataList.get(playerID).revLightsPercent > 60){
                        gear.setTextColor(getResources().getColor(R.color.white,getResources().newTheme()));
                        limiter.setBackgroundColor(getResources().getColor(R.color.red,getResources().newTheme()));
                    }
                    else{
                        gear.setTextColor(getResources().getColor(R.color.red,getResources().newTheme()));
                        limiter.setBackgroundColor(getResources().getColor(R.color.black,getResources().newTheme()));
                    }

                    if(telemetry_packet.TelemetryDataList.get(playerID).speed > topSpeed){
                        topSpeed = telemetry_packet.TelemetryDataList.get(playerID).speed;
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
            case 1:
                session_data = new Session_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
                session_Received = true;
                break;
            case 2:
                lap_packet = new Lap_Packet(packet);
                lap_Received = true;
                if(lap_packet.LapDataList.get(playerID).currentLapNum > 1){
                    if(lap_packet.LapDataList.get(playerID).sector1Time != 0.0) {
                        if (BestS1 > lap_packet.LapDataList.get(playerID).sector1Time) {
                            BestS1 = lap_packet.LapDataList.get(playerID).sector1Time;
                        }
                    }
                    if(lap_packet.LapDataList.get(playerID).sector2Time != 0.0){
                        if (BestS2 > lap_packet.LapDataList.get(playerID).sector2Time) {
                            BestS2 = lap_packet.LapDataList.get(playerID).sector2Time;
                        }
                    }
                    if(Float.valueOf(lap_packet.LapDataList.get(playerID).getSector3Time(false)) != 0.0) {
                        if (BestS3 > Float.valueOf(lap_packet.LapDataList.get(playerID).getSector3Time(false))) {
                            BestS3 = Float.valueOf(lap_packet.LapDataList.get(playerID).getSector3Time(false));
                        }
                    }
//                    if (lap_packet.LapDataList.get(playerID).currentLapNum > lastLapNum) {
//                        totalLapTime = lap_packet.LapDataList.get(playerID).lastLapTime;
//                        lastLapNum = lap_packet.LapDataList.get(playerID).currentLapNum;
//                    }
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
                participants_packet = new Participants_Packet(packet);
                participants_Received = true;
                break;
            case 6:
                telemetry_packet = new Telemetry_Packet(packet);
                telemetry_Received = true;
                speedSum += telemetry_packet.TelemetryDataList.get(playerID).speed;
                speedCount++;
                updateView();
                break;
            case 7:
                carStatus_packet = new CarStatus_Packet(packet);
                carStatus_Received = true;
                break;
            default:
                break;
        }
    }
}
