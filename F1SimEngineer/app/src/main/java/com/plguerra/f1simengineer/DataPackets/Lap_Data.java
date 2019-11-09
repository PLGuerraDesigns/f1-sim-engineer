package com.plguerra.f1simengineer.DataPackets;

import com.plguerra.f1simengineer.BuildConfig;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lap_Data {
    float   lastLapTime;	       	// Last lap time in seconds
    float   currentLapTime;	        // Current time around the lap in seconds
    float   bestLapTime;		    // Best lap time of the session in seconds
    float   sector1Time;		    // Sector 1 time in seconds
    float   sector2Time;		    // Sector 2 time in seconds
    float   lapDistance;		    // Distance vehicle is around current lap in metres – could
                                    // be negative if line hasn’t been crossed yet
    float   totalDistance;		    // Total distance travelled in session in metres – could
                                    // be negative if line hasn’t been crossed yet
    float   safetyCarDelta;         // Delta in seconds for safety car
    short   carPosition;   	        // Car race position
    short   currentLapNum;		    // Current lap number
    short   pitStatus;            	// 0 = none, 1 = pitting, 2 = in pit area
    short   sector;               	// 0 = sector1, 1 = sector2, 2 = sector3
    short   currentLapInvalid;    	// Current lap invalid - 0 = valid, 1 = invalid
    short   penalties;            	// Accumulated time penalties in seconds to be added
    short   gridPosition;         	// Grid position the vehicle started the race in
    short   driverStatus;         	// Status of driver - 0 = in garage, 1 = flying lap
                                    // 2 = in lap, 3 = out lap, 4 = on track
    short   resultStatus;           // Result status - 0 = invalid, 1 = inactive, 2 = active
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


    public String getSector1Time(boolean large){
        if(getSector() == 1){
            float sector1 = currentLapTime;
            return formatSeconds(sector1, large);
        }else if(getSector() > 1){
            return formatSeconds(this.sector1Time, large);
        }else{
            return null;
        }
    }


    public String getSector2Time(boolean large){
        if(getSector() == 2){
            float sector2 = currentLapTime - sector1Time;
            return formatSeconds(sector2, large);
        }else if(getSector() > 2){
            return formatSeconds(this.sector2Time, large);
        }else{
            return null;
        }
    }


    public String getSector3Time(boolean large){
        if(getSector() == 3){
            float sector3 = currentLapTime - sector1Time - sector2Time;
            return formatSeconds(sector3, large);
        }else{
            return null;
        }
    }


    public float getSector3Float(){
        if(sector == 2){
            return currentLapTime - sector1Time - sector2Time;
        }else{
            return 0f;
        }
    }

    public String getPitStatus(){
        switch(pitStatus){
            case 0: return "";
            case 1: return "IN";
            case 2: return "BOX";
            default: return "** UNK **";
        }
    }


    public int getSector(){
        return sector + 1;
    }


    public boolean getCurrentLapInvalid(){
        return currentLapInvalid == 1;
    }

    public String getDriverStatus(){
        switch(driverStatus){
            case 0: return "GARAGE";
            case 1: return "FLYING LAP";
            case 2: return "IN LAP";
            case 3: return "OUT LAP";
            case 4: return "ON TRACK";
            default: return "";
        }
    }


    public String getResultStatus(){
        switch(driverStatus){
            case 0: return "INV";
            case 1: return "INA";
            case 2: return "ACT";
            case 3: return "FIN";
            case 4: return "DSQ";
            case 5: return "DNC";
            case 6: return "DNF";
            default: return "";
        }
    }

    @Override
    public String toString(){
        String ret = "Last lap time: "+getLastLapTime(true)+"\n";
        ret += "Current lap time: "+getCurrentLapTime(true)+"\n";
        ret += "Best lap time: "+getBestLapTime(true)+"\n";
        ret += "Sector 1 time: "+getSector1Time(true)+"\n";
        ret += "Sector 2 time: "+getSector2Time(true)+"\n";
        ret += "Lap distance: "+lapDistance+" m\n";
        ret += "Total distance: "+totalDistance+" m\n";
        ret += "SC Delta: "+safetyCarDelta+"\n";
        ret += "Car position: "+carPosition+"\n";
        ret += "Current Lap: "+currentLapNum+"\n";
        ret += "Pit status: "+getPitStatus() +"\n";
        ret += "Sector: "+getSector() +"\n";
        ret += "Current Lap Invalid: "+getCurrentLapInvalid()+"\n";
        ret += "Penalties: "+penalties+"\n";
        ret += "Grid Position: "+gridPosition+"\n";
        ret += "Driver Status: "+getDriverStatus()+"\n";
        ret += "Result Status: "+getResultStatus()+"\n";

        return ret;
    }
}
