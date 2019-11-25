package com.plguerra.f1simengineer;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.plguerra.f1simengineer.DataPackets.CarSetup_Data;
import com.plguerra.f1simengineer.DataPackets.CarStatus_Data;
import com.plguerra.f1simengineer.DataPackets.Lap_Data;
import com.plguerra.f1simengineer.DataPackets.Motion_Data;
import com.plguerra.f1simengineer.DataPackets.PacketHeader;
import com.plguerra.f1simengineer.DataPackets.Session_Data;
import com.plguerra.f1simengineer.DataPackets.Telemetry_Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class Dashboard extends AppCompatActivity{
    public TextView gear;
    public TextView speed;
    public TextView position;
    public TextView laps;
    public TextView tyre_health_FL;
    public TextView tyre_health_FR;
    public TextView tyre_health_RL;
    public TextView tyre_health_RR;
    public TextView time;
    public TextView lapDelta;
    public TextView car_Ahead;
    public TextView car_Behind;



    public static int MAX_BUFFER = 2048;
    private static final String TAG = "Dashboard";
    PacketHeader packet;
    CarSetup_Data carsetup_data;
    CarStatus_Data carstatus_data;
    public TextView dataView;
    final Handler handler = new Handler();
    Lap_Data lap_data;
    Motion_Data motion_data;
    public Boolean running;
    Session_Data session_data;
    Telemetry_Data telemetry_data;


    public void onStop() {
        super.onStop();
        running = Boolean.valueOf(false);
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_f1);
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
        lapDelta = findViewById(R.id.LapDelta);
        car_Ahead = findViewById(R.id.Car_Ahead);
        car_Behind = findViewById(R.id.Car_Behind);


//        dataView = findViewById(R.id.data);

//        AddSessionData();
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

                        packet = new PacketHeader(msg);
                        UnpackData(packet.packetId, msg);
                        updateView();

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
        ContentValues myCV = new ContentValues();
        myCV.put(DataProvider.PHOTO_TABLE_COL_DATE, "09/25/2019");
        myCV.put(DataProvider.PHOTO_TABLE_COL_SESSTYPE, "Practice");
        myCV.put(DataProvider.PHOTO_TABLE_COL_TRACK, "Melbourne");
        myCV.put(DataProvider.PHOTO_TABLE_COL_TEAM, "Mercedes");
        myCV.put(DataProvider.PHOTO_TABLE_COL_TYRETYPE, "SS");
        myCV.put(DataProvider.PHOTO_TABLE_COL_LAPS, "15");
        myCV.put(DataProvider.PHOTO_TABLE_COL_TOPSPEED, "215");
        myCV.put(DataProvider.PHOTO_TABLE_COL_AVGSPEED, "190");
        myCV.put(DataProvider.PHOTO_TABLE_COL_POSITION, "2");
        myCV.put(DataProvider.PHOTO_TABLE_COL_SESSTIME, "25:15.210");
        myCV.put(DataProvider.PHOTO_TABLE_COL_BESTLAP, "1:28.485");
        myCV.put(DataProvider.PHOTO_TABLE_COL_AVGTIME, "1:23.008");
        myCV.put(DataProvider.PHOTO_TABLE_COL_BESTSECTOR1, "27.456");
        myCV.put(DataProvider.PHOTO_TABLE_COL_BESTSECTOR2, "22.782");
        myCV.put(DataProvider.PHOTO_TABLE_COL_BESTSECTOR3, "23.991");
        getContentResolver().insert(DataProvider.CONTENT_URI, myCV);
    }

    private void updateUI(final String stringData) {
        handler.post(new Runnable() {
            public void run() {
                if (stringData.trim().length() != 0) {
                    gear.setText(stringData);
                }
            }
        });
    }

    private void updateView() {
        handler.post(new Runnable() {
            public void run() {
                try {
                    gear.setText(telemetry_data.getGear());
                    speed.setText(String.valueOf(telemetry_data.speed));
                    position.setText(String.valueOf(lap_data.carPosition));
                    laps.setText(lap_data.currentLapNum + "/" +
                            session_data.totalLaps);
                    tyre_health_RL.setText(String.valueOf(100-carstatus_data.tyresWear[0]));
                    tyre_health_RR.setText(String.valueOf(100-carstatus_data.tyresWear[1]));
                    tyre_health_FL.setText(String.valueOf(100-carstatus_data.tyresWear[2]));
                    tyre_health_FR.setText(String.valueOf(100-carstatus_data.tyresWear[3]));
                    time.setText(String.valueOf(lap_data.getCurrentLapTime(true)));
//                    lapDelta.setText(String.valueOf(lap_data.getBestLapTime(true)));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public void UnpackData(short packetId, byte[] packet) {
        String str = TAG;
        switch (packetId) {
            case 0:
//                Log.d(str, "Motion");
                motion_data = new Motion_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
//                updateUI(motion_data.toString());
                return;
            case 1:
//                Log.d(str, "Session");
                session_data = new Session_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
//                updateUI(session_data.toString());
                return;
            case 2:
                Log.d(str, "Lap Data");
                lap_data = new Lap_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
//                updateUI(lap_data.toString());
                return;
            case 3:
                //Might not be used
//                Log.d(str, "Event");
                return;
            case 4:
                //Might not be used
//                Log.d(str, "Participants");
                return;
            case 5:
//                Log.d(str, "Car Setups");
                carsetup_data = new CarSetup_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
//                updateUI(carsetup_data.toString());
                return;
            case 6:
                Log.d(str, "Car Telemetry");
                telemetry_data = new Telemetry_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
//                updateUI(telemetry_data.toString());
//                    updateUI(telemetry_data.getGear());
//                    SimpleDash.updateSimpleFragment(telemetry_data.getGear());
                return;
            case 7:
//                Log.d(str, "Car Status");
                carstatus_data = new CarStatus_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
//                updateUI(carstatus_data.toString());
                return;
            default:
                return;
        }
    }
}
