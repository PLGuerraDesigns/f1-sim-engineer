package com.plguerra.f1simengineer.DataPackets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Telemetry_Data {
    public static int     speed;                      // Speed of car in kilometres per hour
    public static float   throttle;                   // Amount of throttle applied (0.0 to 1.0)
    public static float   steer;                      // Steering (-1.0 (full lock left) to 1.0 (full lock right))
    public static float   brake;                      // Amount of brake applied (0.0 to 1.0)
    public static short   clutch;                     // Amount of clutch applied (0 to 100)
    public static short   gear;                       // Gear selected (1-8, N=0, R=-1)
    public static int     engineRPM;                  // Engine RPM
    public static short   drs;                        // 0 = off, 1 = on
    public static short   revLightsPercent;           // Rev lights indicator (percentage)
    public static int     brakesTemperature[];        // Brakes temperature (celsius)
    public static int     tyresSurfaceTemperature[];  // Tyres surface temperature (celsius)
    public static int     tyresInnerTemperature[];    // Tyres inner temperature (celsius)
    public static int     engineTemperature;          // Engine temperature (celsius)
    public static float   tyresPressure[];            // Tyres pressure (PSI)
    public static short   surfaceType[];              // Driving surface, see appendices
    

    public Telemetry_Data(byte[] content) {
        ByteBuffer bb = ByteBuffer.wrap(content);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        
        speed = bb.getShort();
        throttle = bb.getFloat();
        steer = bb.getFloat();
        brake = bb.getFloat();
        clutch = bb.get();
        gear = bb.get();
        engineRPM = bb.getShort();
        drs = bb.get();
        revLightsPercent = bb.get();
        brakesTemperature = new int[4];
        brakesTemperature[0] = bb.getShort();
        brakesTemperature[1] = bb.getShort();
        brakesTemperature[2] = bb.getShort();
        brakesTemperature[3] = bb.getShort();
        tyresSurfaceTemperature = new int[4];
        tyresSurfaceTemperature[0] = bb.getShort();
        tyresSurfaceTemperature[1] = bb.getShort();
        tyresSurfaceTemperature[2] = bb.getShort();
        tyresSurfaceTemperature[3] = bb.getShort();
        tyresInnerTemperature = new int[4];
        tyresInnerTemperature[0] = bb.getShort();
        tyresInnerTemperature[1] = bb.getShort();
        tyresInnerTemperature[2] = bb.getShort();
        tyresInnerTemperature[3] = bb.getShort();
        engineTemperature = bb.getShort();
        tyresPressure = new float[4];
        tyresPressure[0] = bb.getFloat();
        tyresPressure[1] = bb.getFloat();
        tyresPressure[2] = bb.getFloat();
        tyresPressure[3] = bb.getFloat();
        surfaceType = new short[4];
        surfaceType[0] = bb.get();
        surfaceType[1] = bb.get();
        surfaceType[2] = bb.get();
        surfaceType[3] = bb.get();
    }

    public String getDRS(){
        switch(drs){
            case 0: return "OFF";
            case 1: return "ON";
            default: return "";
        }
    }

    public String getGear(){
        switch(gear){
            case -1: return "R";
            case 0: return "N";
            case 1: return "1";
            case 2: return "2";
            case 3: return "3";
            case 4: return "4";
            case 5: return "5";
            case 6: return "6";
            case 7: return "7";
            case 8: return "8";
            default: return "-";
        }
    }


    public String toString(){
        String ret = "Speed: "+speed+" kpm\n";
        ret += "Throttle: "+throttle+"\n";
        ret += "Steer: "+steer+"\n";
        ret += "Brake: "+brake+"\n";
        ret += "Clutch: "+clutch+"\n";
        ret += "Gear: "+gear+"\n";


        ret += "Engine RPM: "+engineRPM+" rpm\n";
        ret += "DRS: "+getDRS()+"\n";
        ret += "Rev lights Percent: "+revLightsPercent+" %\n";
        ret += "Brakes Temperature:\n";
        ret += " - RL: "+brakesTemperature[0]+" ºC\n";
        ret += " - RL: "+brakesTemperature[1]+" ºC\n";
        ret += " - RL: "+brakesTemperature[2]+" ºC\n";
        ret += " - RL: "+brakesTemperature[3]+" ºC\n";
        ret += "Tyres Surface Temperature:\n";
        ret += " - RL: "+tyresSurfaceTemperature[0]+" ºC\n";
        ret += " - RL: "+tyresSurfaceTemperature[1]+" ºC\n";
        ret += " - RL: "+tyresSurfaceTemperature[2]+" ºC\n";
        ret += " - RL: "+tyresSurfaceTemperature[3]+" ºC\n";
        ret += "Tyres Inner Temperature:\n";
        ret += " - RL: "+tyresInnerTemperature[0]+" ºC\n";
        ret += " - RL: "+tyresInnerTemperature[1]+" ºC\n";
        ret += " - RL: "+tyresInnerTemperature[2]+" ºC\n";
        ret += " - RL: "+tyresInnerTemperature[3]+" ºC\n";
        ret += "Engine Temperature: "+engineTemperature+" ºC\n";
        ret += "Tyres Pressure:\n";
        ret += " - RL: "+tyresPressure[0]+" psi\n";
        ret += " - RL: "+tyresPressure[1]+" psi\n";
        ret += " - RL: "+tyresPressure[2]+" psi\n";
        ret += " - RL: "+tyresPressure[3]+" psi\n";


        return ret;
    }
}
