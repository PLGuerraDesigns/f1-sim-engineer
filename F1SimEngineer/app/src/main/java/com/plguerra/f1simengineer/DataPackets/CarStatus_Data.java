package com.plguerra.f1simengineer.DataPackets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CarStatus_Data {
    public short   tractionControl;        // 0 (off) - 2 (high)
    public short   antiLockBrakes;         // 0 (off) - 1 (on)
    public short   fuelMix;                // Fuel mix - 0 = lean, 1 = standard, 2 = rich, 3 = max
    public short   frontBrakeBias;         // Front brake bias (percentage)
    public short   pitLimiterStatus;       // Pit limiter status - 0 = off, 1 = on
    public float   fuelInTank;             // Current fuel mass
    public float   fuelCapacity;           // Fuel capacity
    public float   fuelRemainingLaps;      // Fuel remaining in terms of laps (value on MFD)
    public int     maxRPM;                 // Cars max RPM, point of rev limiter
    public int     idleRPM;                // Cars idle RPM
    public short   maxGears;               // Maximum number of gears
    public short   drsAllowed;             // 0 = not allowed, 1 = allowed, -1 = unknown
    public short   tyresWear[];            // Tyre wear percentage
    public short   actualTyreCompound;	    // F1 Modern - 16 = C5, 17 = C4, 18 = C3, 19 = C2, 20 = C1
                                    // 7 = inter, 8 = wet
                                    // F1 Classic - 9 = dry, 10 = wet
                                    // F2 – 11 = super soft, 12 = soft, 13 = medium, 14 = hard
                                    // 15 = wet
    public short   tyreVisualCompound;     // F1 visual (can be different from actual compound)
                                    // 16 = soft, 17 = medium, 18 = hard, 7 = inter, 8 = wet
                                    // F1 Classic – same as above
                                    // F2 – same as above
    public short   tyresDamage[];          // Tyre damage (percentage)
    public short   frontLeftWingDamage;    // Front left wing damage (percentage)
    public short   frontRightWingDamage;   // Front right wing damage (percentage)
    public short   rearWingDamage;         // Rear wing damage (percentage)
    public short   engineDamage;           // Engine damage (percentage)
    public short   gearBoxDamage;          // Gear box damage (percentage)
    public short   vehicleFiaFlags;	    // -1 = invalid/unknown, 0 = none, 1 = green
                                    // 2 = blue, 3 = yellow, 4 = red
    public float   ersStoreEnergy;         // ERS energy store in Joules
    public short   ersDeployMode;          // ERS deployment mode, 0 = none, 1 = low, 2 = medium
                                    // 3 = high, 4 = overtake, 5 = hotlap
    public float   ersHarvestedThisLapMGUK;// ERS energy harvested this lap by MGU-K
    public float   ersHarvestedThisLapMGUH;// ERS energy harvested this lap by MGU-H
    public float   ersDeployedThisLap;     // ERS energy deployed this lap


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
     maxRPM = bb.get();
     idleRPM = bb.get();
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

    public String getTractionControl(){
        switch(tractionControl){
            case 0: return "OFF";
            case 1: return "Medium";
            case 2: return "High";
            default: return "";
        }
    }

    public String getAntiLockBrakes(){
        switch(antiLockBrakes){
            case 0: return "OFF";
            case 1: return "ON";
            default: return "";
        }
    }

    public String getFuelMix(){
        switch(fuelMix){
            case 0: return "Lean";
            case 1: return "Standard";
            case 2: return "Rich";
            case 3: return "MAX";
            default: return "";
        }
    }

    public String getPitLimiter(){
        switch(pitLimiterStatus){
            case 0: return "OFF";
            case 1: return "ON";
            default: return "";
        }
    }

    public String getDRSAllowed(){
        switch(drsAllowed){
            case 0: return "NOT ALLOWED";
            case 1: return "ALLOWED";
            default: return "";
        }
    }

    public String getTyreCompound(){
        switch(actualTyreCompound){
            case 0: return "HYPER SOFT";
            case 1: return "ULTRA SOFT";
            case 2: return "SUPER SOFT";
            case 3: return "SOFT";
            case 4: return "MEDIUM";
            case 5: return "HARD";
            case 6: return "SUPER HARD";
            case 7: return "INTER";
            case 8: return "WET";
            default: return "";
        }
    }

    public String getVehicleFiaFlag(){
        switch(vehicleFiaFlags){
            case 0: return "NONE";
            case 1: return "GREEN";
            case 2: return "BLUE";
            case 3: return "YELLOW";
            case 4: return "RED";
            default: return "";
        }
    }

    public String getERSDeployMode(){
        switch(ersDeployMode){
            case 0: return "None";
            case 1: return "Low";
            case 2: return "Medium";
            case 3: return "High";
            case 4: return "Overtake";
            case 5: return "Hotlap";
            default: return "";
        }
    }

    public String toString(){
        String ret = "Traction control: "+getTractionControl()+"\n";
        ret += "AntiLock Brakes: "+getAntiLockBrakes()+"\n";
        ret += "Fuel Mix: "+getFuelMix()+"\n";
        ret += "Front Brake Bias:"+frontBrakeBias+" %\n";
        ret += "Pit limiter: "+getPitLimiter()+"\n";
        ret += "Fuel in tank: "+fuelInTank+" Kg \n";
        ret += "Fuel capacity: "+fuelCapacity+" Kg \n";
        ret += "Max. rpm: "+maxRPM+" rpm\n";
        ret += "Idle rpm: "+idleRPM+" rpm\n";
        ret += "Max. gears: "+maxGears+"\n";
        ret += "DRS: "+getDRSAllowed()+"\n";

        ret += "Tyres %:\n";
        ret += " RL "+tyresWear[0]+"\n";
        ret += " RR "+tyresWear[1]+"\n";
        ret += " FL "+tyresWear[2]+"\n";
        ret += " FR "+tyresWear[3]+"\n";

        ret += "Tyres (Compound): "+getTyreCompound()+"\n";

        ret += "Tyres Damage (%):\n";
        ret += " RL "+tyresDamage[0]+" % \n";
        ret += " RR "+tyresDamage[1]+" % \n";
        ret += " FL "+tyresDamage[2]+" % \n";
        ret += " FR "+tyresDamage[3]+" % \n";

        ret += "Front Left Wing Damage: "+frontLeftWingDamage+" %\n";
        ret += "Front Right Wing Damage: "+frontRightWingDamage+" %\n";

        ret += "Rear Wing Damage: "+rearWingDamage+" %\n";
        ret += "Engine Damage: "+engineDamage+" %\n";
        ret += "Gear Box Damage: "+gearBoxDamage+" %\n";

        ret += "Vehicle Flag: "+getVehicleFiaFlag()+"\n";

        ret += "ERS Storage Energy: "+ersStoreEnergy+" Jules\n";

        ret += "ERS Deploy Mode: "+getERSDeployMode()+"\n";

        ret += "ERS Harvested This Lap MGU-K: "+ersHarvestedThisLapMGUK+"\n";
        ret += "ERS Harvested This Lap MGU-H: "+ersHarvestedThisLapMGUH+"\n";
        ret += "ERS Deployed This Lap: "+ersDeployedThisLap+"\n";
        return ret;
    }
}
