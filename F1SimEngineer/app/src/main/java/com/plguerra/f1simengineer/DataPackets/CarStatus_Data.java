package com.plguerra.f1simengineer.DataPackets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CarStatus_Data {
    public static int SIZE = 56;

    public short   tractionControl;         // 0 (off) - 2 (high)
    public short   antiLockBrakes;          // 0 (off) - 1 (on)
    public short   fuelMix;                 // Fuel mix - 0 = lean, 1 = standard, 2 = rich, 3 = max
    public short   frontBrakeBias;          // Front brake bias (percentage)
    public short   pitLimiterStatus;        // Pit limiter status - 0 = off, 1 = on
    public float   fuelInTank;              // Current fuel mass
    public float   fuelCapacity;            // Fuel capacity
    public float   fuelRemainingLaps;       // Fuel remaining in terms of laps (value on MFD)
    public int     maxRPM;                  // Cars max RPM, point of rev limiter
    public int     idleRPM;                 // Cars idle RPM
    public short   maxGears;                // Maximum number of gears
    public short   drsAllowed;              // 0 = not allowed, 1 = allowed, -1 = unknown
    public short   tyresWear[];             // Tyre wear percentage
    public short   actualTyreCompound;	    // F1 Modern - 16 = C5, 17 = C4, 18 = C3, 19 = C2, 20 = C1
                                            // 7 = inter, 8 = wet
                                            // F1 Classic - 9 = dry, 10 = wet
                                            // F2 – 11 = super soft, 12 = soft, 13 = medium, 14 = hard
                                            // 15 = wet
    public short   tyreVisualCompound;      // F1 visual (can be different from actual compound)
                                            // 16 = soft, 17 = medium, 18 = hard, 7 = inter, 8 = wet
                                            // F1 Classic – same as above
                                            // F2 – same as above
    public short   tyresDamage[];           // Tyre damage (percentage)
    public short   frontLeftWingDamage;     // Front left wing damage (percentage)
    public short   frontRightWingDamage;    // Front right wing damage (percentage)
    public short   rearWingDamage;          // Rear wing damage (percentage)
    public short   engineDamage;            // Engine damage (percentage)
    public short   gearBoxDamage;           // Gear box damage (percentage)
    public short   vehicleFiaFlags;	        // -1 = invalid/unknown, 0 = none, 1 = green
                                            // 2 = blue, 3 = yellow, 4 = red
    public float   ersStoreEnergy;          // ERS energy store in Joules
    public short   ersDeployMode;           // ERS deployment mode, 0 = none, 1 = low, 2 = medium
                                            // 3 = high, 4 = overtake, 5 = hotlap
    public float   ersHarvestedThisLapMGUK; // ERS energy harvested this lap by MGU-K
    public float   ersHarvestedThisLapMGUH; // ERS energy harvested this lap by MGU-H
    public float   ersDeployedThisLap;      // ERS energy deployed this lap


    public CarStatus_Data(byte[] content) {
     ByteBuffer bb = ByteBuffer.wrap(content);
     bb.order(ByteOrder.LITTLE_ENDIAN);

     tractionControl = bb.get();
     antiLockBrakes = bb.get();
     fuelMix = bb.get();
     frontBrakeBias = bb.get();
     pitLimiterStatus = bb.get();
     fuelInTank = bb.getFloat();
     fuelCapacity = bb.getFloat();
     fuelRemainingLaps = bb.getFloat();
     maxRPM = bb.getShort();
     idleRPM = bb.getShort();
     maxGears = bb.get();
     drsAllowed = bb.get();
     tyresWear = new short[4];
     tyresWear[0] = bb.get();
     tyresWear[1] = bb.get();
     tyresWear[2] = bb.get();
     tyresWear[3] = bb.get();
     actualTyreCompound = bb.get();
     tyreVisualCompound = bb.get();
     tyresDamage = new short[4];
     tyresDamage[0] = bb.get();
     tyresDamage[1] = bb.get();
     tyresDamage[2] = bb.get();
     tyresDamage[3] = bb.get();
     frontLeftWingDamage = bb.get();
     frontRightWingDamage = bb.get();
     rearWingDamage = bb.get();
     engineDamage = bb.get();
     gearBoxDamage = bb.get();
     vehicleFiaFlags = bb.get();
     ersStoreEnergy = bb.getFloat();
     ersDeployMode = bb.get();
     ersHarvestedThisLapMGUK = bb.getFloat();
     ersHarvestedThisLapMGUH = bb.getFloat();
     ersDeployedThisLap = bb.getFloat();
    }


    public String getTyreCompound(){
        switch(actualTyreCompound){
            case 16: case 11: return "C5";
            case 17: case 12: return "C4";
            case 18: return "C3";
            case 19: case 14: case 9:return "C2";
            case 20: return "C1";
            case 15: case 10: case 8:return "Wet";
            case 7: return "Inter";
            default: return "";
        }
    }

}
