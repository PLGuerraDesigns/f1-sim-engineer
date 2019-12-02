package com.plguerra.f1simengineer.DataPackets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Session_Data {
    public short   weather;              	// Weather - 0 = clear, 1 = light cloud, 2 = overcast
                                            // 3 = light rain, 4 = heavy rain, 5 = storm
    public short   trackTemperature;    	// Track temp. in degrees celsius
    public short   airTemperature;      	// Air temp. in degrees celsius
    public short   totalLaps;           	// Total number of laps in this race
    public int     trackLength;           	// Track length in metres
    public short   sessionType;         	// 0 = unknown, 1 = P1, 2 = P2, 3 = P3, 4 = Short P
                                            // 5 = Q1, 6 = Q2, 7 = Q3, 8 = Short Q, 9 = OSQ
                                            // 10 = R, 11 = R2, 12 = Time Trial
    public short   trackId;         		// -1 for unknown, 0-21 for tracks, see appendix
    public short   formula;                 // Formula, 0 = F1 Modern, 1 = F1 Classic, 2 = F2,
                                            // 3 = F1 Generic
    public int     sessionTimeLeft;    	    // Time left in session in seconds
    public int     sessionDuration;     	// Session duration in seconds
    public short   pitSpeedLimit;      	    // Pit speed limit in kilometres per hour
    public short   gamePaused;              // Whether the game is paused
    public short   isSpectating;            // Whether the player is spectating
    public short   spectatorCarIndex;  	    // Index of the car being spectated
    public short   sliProNativeSupport;	    // SLI Pro support, 0 = inactive, 1 = active
    public short   numMarshalZones;         // Number of marshal zones to follow

    public Session_Data(byte[] content) {
        ByteBuffer bb = ByteBuffer.wrap(content);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        weather = (short) bb.get();
        trackTemperature = (short) bb.get();
        airTemperature = (short) bb.get();
        totalLaps = (short) bb.get();
        trackLength = bb.getShort();
        sessionType = (short) bb.get();
        trackId = (short) bb.get();
        formula = (short) bb.get();
        sessionTimeLeft = bb.getShort();
        sessionDuration = bb.getShort();
        pitSpeedLimit = (short) bb.get();
        gamePaused = (short) bb.get();
        isSpectating = (short) bb.get();
        spectatorCarIndex = (short) bb.get();
        sliProNativeSupport = (short) bb.get();
        numMarshalZones = (short) bb.get();
    }


    public String getTrack(){
        switch(trackId){
            case 0: return "Melbourne";
            case 1: return "Paul Ricard";
            case 2: return "Shanghai";
            case 3: return "Sakhir";
            case 4: return "Catalunya";
            case 5: return "Monaco";
            case 6: return "Montreal";
            case 7: return "Silverstone";
            case 8: return "Hockenheim";
            case 9: return "Hungaroring";
            case 10: return "Spa";
            case 11: return "Monza";
            case 12: return "Singapore";
            case 13: return "Suzuka";
            case 14: return "Abu Dhabi";
            case 15: return "Texas";
            case 16: return "Brazil";
            case 17: return "Austria";
            case 18: return "Sochi";
            case 19: return "Mexico";
            case 20: return "Baku";
            case 21: return "Sakhir (short)";
            case 22: return "Silverstone (short)";
            case 23: return "Texas (short)";
            case 24: return "Suzuka (short)";
            default: return "";
        }
    }


    public String getSessionType(){
        switch(sessionType){
            case 1: return "FP1";
            case 2: return "FP2";
            case 3: return "FP3";
            case 4: return "Short Practice";
            case 5: return "Q1";
            case 6: return "Q2";
            case 7: return "Q3";
            case 8: return "Short Qualifying";
            case 9: return "OSQ";
            case 10: return "Race";
            case 11: return "R2";
            case 12: return "Time Trial";
            default: return "";
        }
    }

    public String getSessionDuration(){
        return printFormattedSeconds(sessionDuration);
    }


    public static String printFormattedSeconds(int sec){
        int hours = (int) (sec / 3600);
        int minutes = (int) (sec / 60);
        int seconds = sec % 60;
        if(hours != 0){
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        }else
            return String.format("%d:%02d", minutes, seconds);
    }

}
