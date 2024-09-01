package com.plguerra.f1simengineer;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity{
    SharedPreferences sharedPref;
    TextView ipInfo;
    EditText portInfo;
    Button save;
    String Port;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ipInfo = findViewById(R.id.IP_Info);
        portInfo = findViewById(R.id.Port_Info);
        save = findViewById(R.id.saveButton);


        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipInt = wifiInfo.getIpAddress();
        String ip = null;
        //Display Device IP
        try {
            ip = InetAddress.getByAddress(
                    ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipInt).array())
                    .getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ipInfo.setText(ip);
        //Display Port
        portInfo.setText(sharedPref.getString("PortInfo","20777"));
        //Save port value to device
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Port = portInfo.getText().toString();
                if(Port.length() == 0){
                    Port = "20777";
                }
                sharedPref.edit().putString("PortInfo", Port).commit();
                Toast.makeText(SettingsActivity.this,"Port " + Port + " saved.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }


}
