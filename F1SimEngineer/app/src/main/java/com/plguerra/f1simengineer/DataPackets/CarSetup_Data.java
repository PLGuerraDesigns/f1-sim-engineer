package com.plguerra.f1simengineer.DataPackets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CarSetup_Data {
    short     frontWing;                // Front wing aero
    short     rearWing;                 // Rear wing aero
    short     onThrottle;               // Differential adjustment on throttle (percentage)
    short     offThrottle;              // Differential adjustment off throttle (percentage)
    float     frontCamber;              // Front camber angle (suspension geometry)
    float     rearCamber;               // Rear camber angle (suspension geometry)
    float     frontToe;                 // Front toe angle (suspension geometry)
    float     rearToe;                  // Rear toe angle (suspension geometry)
    short     frontSuspension;          // Front suspension
    short     rearSuspension;           // Rear suspension
    short     frontAntiRollBar;         // Front anti-roll bar
    short     rearAntiRollBar;          // Front anti-roll bar
    short     frontSuspensionHeight;    // Front ride height
    short     rearSuspensionHeight;     // Rear ride height
    short     brakePressure;            // Brake pressure (percentage)
    short     brakeBias;                // Brake bias (percentage)
    float     frontTyrePressure;        // Front tyre pressure (PSI)
    float     rearTyrePressure;         // Rear tyre pressure (PSI)
    short     ballast;                  // Ballast
    float     fuelLoad;                 // Fuel load

    public CarSetup_Data(byte[] content) {
        ByteBuffer bb = ByteBuffer.wrap(content);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        frontWing = bb.get();
        rearWing = bb.get();
        onThrottle =  bb.get();
        offThrottle = bb.get();
        frontCamber = bb.getFloat();
        rearCamber = bb.getFloat();
        frontToe = bb.getFloat();
        rearToe = bb.getFloat();
        frontSuspension = bb.get();
        rearSuspension = bb.get();
        frontAntiRollBar = bb.get();
        rearAntiRollBar = bb.get();
        frontSuspensionHeight = bb.get();
        rearSuspensionHeight = bb.get();
        brakePressure = bb.get();
        brakeBias = bb.get();
        frontTyrePressure = bb.getFloat();
        rearTyrePressure = bb.getFloat();
        ballast = bb.get();
        fuelLoad = bb.getFloat();
    }

    public String toString(){
        String ret = "Front Wing: "+frontWing+"\n";
        ret += "Rear Wing: "+rearWing+"\n";
        ret += "On throttle: "+onThrottle+"\n";
        ret += "Off throttle: "+offThrottle+"\n";
        ret += "Front camber: "+frontCamber+"\n";
        ret += "Rear camber: "+rearCamber+"\n";
        ret += "Front toe: "+frontToe+"\n";
        ret += "Rear toe: "+rearToe+"\n";
        ret += "Front suspension: "+frontSuspension+"\n";
        ret += "Rear suspension: "+rearSuspension+"\n";
        ret += "Front antirollbar: "+frontAntiRollBar+"\n";
        ret += "Rear antirollbar: "+rearAntiRollBar+"\n";
        ret += "Front suspension height: "+frontSuspensionHeight+"\n";
        ret += "Rear suspension height: "+rearSuspensionHeight+"\n";
        ret += "Brake pressure: "+brakePressure+"\n";
        ret += "Brake bias: "+brakeBias+"\n";
        ret += "Front tyre pressure: "+frontTyrePressure+"\n";
        ret += "Rear tyre pressure: "+rearTyrePressure+"\n";
        ret += "Ballast: "+ballast+"\n";
        ret += "Fuel load: "+fuelLoad+"\n";
        return ret;
    }
}
