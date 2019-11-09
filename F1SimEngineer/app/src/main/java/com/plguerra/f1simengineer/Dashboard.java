package com.plguerra.f1simengineer;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.Arrays;

public class Dashboard extends AppCompatActivity {
    public static int MAX_BUFFER = 2048;
    private static final String TAG = "Dashboard";
    CarSetup_Data carsetup_data;
    CarStatus_Data carstatus_data;
    public TextView dataView;
    public TextView gearView;
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
        setContentView(R.layout.dashboard_simple);

        gearView = findViewById(R.id.gear);

        dataView = findViewById(R.id.data);
//        AddSessionData();
        startServerSocket();
        running = Boolean.valueOf(true);
    }

    private void startServerSocket() {
        new Thread(new Runnable() {
            PacketHeader packet;
            private String stringData = null;

            public void run() {
                while (running.booleanValue()) {
                    byte[] msg = new byte[Dashboard.MAX_BUFFER];
                    DatagramPacket datagramPacket = new DatagramPacket(msg, msg.length);
                    DatagramSocket datagramSocket = null;
                    try {
                        datagramSocket = new DatagramSocket(20777);
                        datagramSocket.receive(datagramPacket);
                        stringData = new String(msg, 0, datagramPacket.getLength());
                        packet = new PacketHeader(msg);
                        UnpackData(packet.packetId, msg);

//                        updateUI(packet.toString());
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (datagramSocket == null) {
                        }
                    } catch (Throwable th) {
                        if (datagramSocket != null) {
                            datagramSocket.close();
                        }
                        throw th;
                    }
                    datagramSocket.close();
                }
            }
        }).start();
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
                    dataView.setText(stringData);
                }
            }
        });
    }


    public void UnpackData(short packetId, byte[] packet) {
        String str = TAG;
        switch (packetId) {
            case 0:
                Log.d(str, "Motion");
                motion_data = new Motion_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
//                updateUI(motion_data.toString());
                return;
            case 1:
                Log.d(str, "Session");
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
                Log.d(str, "Event");
                return;
            case 4:
                //Might not be used
                Log.d(str, "Participants");
                return;
            case 5:
                Log.d(str, "Car Setups");
                carsetup_data = new CarSetup_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
//                updateUI(carsetup_data.toString());
                return;
            case 6:
                Log.d(str, "Car Telemetry");
                telemetry_data = new Telemetry_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
                updateUI(telemetry_data.toString());
                return;
            case 7:
                Log.d(str, "Car Status");
                carstatus_data = new CarStatus_Data(Arrays.copyOfRange(packet, PacketHeader.HEADER_SIZE, packet.length - 1));
//                updateUI(carstatus_data.toString());
                return;
            default:
                return;
        }
    }
}
