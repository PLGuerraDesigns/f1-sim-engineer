package com.plguerra.f1simengineer.DataPackets;

import java.util.ArrayList;
import java.util.Arrays;

public class CarStatus_Packet {

        public ArrayList<CarStatus_Data> CarStatusList = new ArrayList<>();


        public CarStatus_Packet(byte[] content) {
            int start = PacketHeader.HEADER_SIZE;
            int end;
            byte[] car_Content;
            int count = 0;
            while (count < 20) {
                end = start + CarStatus_Data.SIZE;

                car_Content = Arrays.copyOfRange(content, start, end);

                CarStatus_Data data = new CarStatus_Data(car_Content);
                CarStatusList.add(data);

                start += CarStatus_Data.SIZE;
                count++;
            }

        }
}
