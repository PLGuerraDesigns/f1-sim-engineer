package com.plguerra.f1simengineer;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
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
    String Port;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ipInfo = findViewById(R.id.IP_Info);
        portInfo = findViewById(R.id.Port_Info);


        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipInt = wifiInfo.getIpAddress();
        String ip = null;
        try {
            ip = InetAddress.getByAddress(
                    ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipInt).array())
                    .getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ipInfo.setText(ip);
        portInfo.setText(sharedPref.getString("PortInfo","2777"));

    }

    @Override
    public void onBackPressed() {
        Port = portInfo.getText().toString();
        if(Port.length() == 0){
            Port = "2777";
        }
        sharedPref.edit().putString("PortInfo", Port).commit();
        Toast.makeText(this,"Port " + Port + " saved.",Toast.LENGTH_SHORT).show();
        finish();
    }

}
