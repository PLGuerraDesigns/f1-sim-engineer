package com.plguerra.f1simengineer.DataPackets;

import java.util.ArrayList;
import java.util.Arrays;

public class Telemetry_Packet {
    public ArrayList<Telemetry_Data> TelemetryDataList = new ArrayList<>();


    public Telemetry_Packet(byte[] content){
        int start = PacketHeader.HEADER_SIZE;
        int end;
        byte[] car_Content;
        int count = 0;
        while(count < 20){
            end = start + Telemetry_Data.SIZE;

            car_Content = Arrays.copyOfRange(content, start, end);

            Telemetry_Data data = new Telemetry_Data(car_Content);
            TelemetryDataList.add(data);

            start += Telemetry_Data.SIZE;
            count ++;
        }

    }
}
