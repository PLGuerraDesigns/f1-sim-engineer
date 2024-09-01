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

}
