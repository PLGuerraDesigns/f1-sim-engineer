package com.plguerra.f1simengineer.DataPackets;

import java.util.ArrayList;
import java.util.Arrays;

public class Lap_Packet {

    public ArrayList<Lap_Data> LapDataList = new ArrayList<>();


    public Lap_Packet(byte[] content) {
        int start = PacketHeader.HEADER_SIZE;
        int end;
        byte[] car_Content;
        int count = 0;
        while (count < 20) {
            end = start + Lap_Data.SIZE;

            car_Content = Arrays.copyOfRange(content, start, end);

            Lap_Data data = new Lap_Data(car_Content);
            LapDataList.add(data);

            start += Lap_Data.SIZE;
            count++;
        }

    }
}
