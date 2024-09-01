package com.plguerra.f1simengineer.DataPackets;

import java.util.ArrayList;
import java.util.Arrays;

public class Participants_Packet {
    public ArrayList<Participants_Data> ParticipantDataList = new ArrayList<>();


    public Participants_Packet(byte[] content){
        int start = PacketHeader.HEADER_SIZE + 1;
        int end;
        byte[] car_Content;
        int count = 0;

        while(count < 20){
            end = start + Participants_Data.SIZE;

            car_Content = Arrays.copyOfRange(content, start, end);

            Participants_Data data = new Participants_Data(car_Content);
            ParticipantDataList.add(data);

            start += Participants_Data.SIZE;
            count ++;
        }

    }
}
