package com.plguerra.f1simengineer.DataPackets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lap_Data {
    public static int SIZE = 41;

    public float   lastLapTime;	       	// Last lap time in seconds
    public float   currentLapTime;	    // Current time around the lap in seconds
    public float   bestLapTime;		    // Best lap time of the session in seconds
    public float   sector1Time;		    // Sector 1 time in seconds
    public float   sector2Time;		    // Sector 2 time in seconds
    public float   lapDistance;		    // Distance vehicle is around current lap in metres – could
                                        // be negative if line hasn’t been crossed yet
    public float   totalDistance;		// Total distance travelled in session in metres – could
                                        // be negative if line hasn’t been crossed yet
    public float   safetyCarDelta;      // Delta in seconds for safety car
    public short   carPosition;   	    // Car race position
    public short   currentLapNum;		// Current lap number
    public short   pitStatus;           // 0 = none, 1 = pitting, 2 = in pit area
    public short   sector;              // 0 = sector1, 1 = sector2, 2 = sector3
    public short   currentLapInvalid;   // Current lap invalid - 0 = valid, 1 = invalid
    public short   penalties;           // Accumulated time penalties in seconds to be added
    public short   gridPosition;        // Grid position the vehicle started the race in
    public short   driverStatus;        // Status of driver - 0 = in garage, 1 = flying lap
                                        // 2 = in lap, 3 = out lap, 4 = on track
    public short   resultStatus;        // Result status - 0 = invalid, 1 = inactive, 2 = active
                                        // 3 = finished, 4 = disqualified, 5 = not classified
                                        // 6 = retired

    public Lap_Data(byte[] content) {
        ByteBuffer bb = ByteBuffer.wrap(content);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        lastLapTime = bb.getFloat();
        currentLapTime = bb.getFloat();
        bestLapTime = bb.getFloat();
        sector1Time = bb.getFloat();
        sector2Time = bb.getFloat();
        lapDistance = bb.getFloat();
        totalDistance = bb.getFloat();
        safetyCarDelta = bb.getFloat();
        carPosition = bb.get();
        currentLapNum = bb.get();
        pitStatus = bb.get();
        sector = bb.get();
        currentLapInvalid = bb.get();
        penalties = bb.get();
        gridPosition = bb.get();
        driverStatus = bb.get();
        resultStatus = bb.get();
    }



    public static String formatSeconds(float seconds, boolean large){
        if(seconds <= 0 || seconds == Float.POSITIVE_INFINITY){
            return "";
        }
        if(large){
            Date date = new Date((long)(seconds * 1000));
            return new SimpleDateFormat("m:ss.SSS").format(date);
        }
        if(seconds >= 60){
            return ((int)(seconds / 60))+((int) seconds)+"."+(int) ((seconds * 10) % 10);
        }
        return ((int) seconds)+"."+(int) ((seconds * 10) % 10);
    }

    public String getLastLapTime(boolean large){

        return formatSeconds(lastLapTime, large);
    }

    public String getCurrentLapTime(boolean large){
        return formatSeconds(currentLapTime, large);
    }

    public String getBestLapTime(boolean large){
        return formatSeconds(bestLapTime, large);
    }


    public String getSector3Time(boolean Formatted){
        if(Formatted ){
            if(getSector() == 3){
                float sector3 = currentLapTime - sector1Time - sector2Time;
                return formatSeconds(sector3, true);
            }
            else{
                return null;
            }
        }
        else{
            float sector3 = currentLapTime - sector1Time - sector2Time;
            return String.valueOf(sector3*10);
        }
    }


    public int getSector(){
        return sector + 1;
    }



}
