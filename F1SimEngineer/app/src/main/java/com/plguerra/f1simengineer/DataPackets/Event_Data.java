package com.plguerra.f1simengineer.DataPackets;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Event_Data {
    public short eventStringCode[];     // Event string code, see below

    public Event_Data(byte[] content){
        ByteBuffer bb = ByteBuffer.wrap(content);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        eventStringCode = new short[4];
        eventStringCode[0] = bb.get();
        eventStringCode[1] = bb.get();
        eventStringCode[2] = bb.get();
        eventStringCode[3] = bb.get();
    }

    public String getEventString(){
        String eventString = "";

        int i;
        for(i = 0; i < eventStringCode.length; i++) {
            eventString += String.valueOf((char)eventStringCode[i]);
        }
        return eventString;
    }
}
