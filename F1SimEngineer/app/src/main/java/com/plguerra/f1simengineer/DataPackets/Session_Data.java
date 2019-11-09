package com.plguerra.f1simengineer.DataPackets;

import com.plguerra.f1simengineer.BuildConfig;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Session_Data {
    short   weather;              	// Weather - 0 = clear, 1 = light cloud, 2 = overcast
                                    // 3 = light rain, 4 = heavy rain, 5 = storm
    short   trackTemperature;    	// Track temp. in degrees celsius
    short   airTemperature;      	// Air temp. in degrees celsius
    short   totalLaps;           	// Total number of laps in this race
    int     trackLength;           	// Track length in metres
    short   sessionType;         	// 0 = unknown, 1 = P1, 2 = P2, 3 = P3, 4 = Short P
                                    // 5 = Q1, 6 = Q2, 7 = Q3, 8 = Short Q, 9 = OSQ
                                    // 10 = R, 11 = R2, 12 = Time Trial
    short   trackId;         		// -1 for unknown, 0-21 for tracks, see appendix
    short   formula;                // Formula, 0 = F1 Modern, 1 = F1 Classic, 2 = F2,
                                    // 3 = F1 Generic
    int     sessionTimeLeft;    	// Time left in session in seconds
    int     sessionDuration;     	// Session duration in seconds
    short   pitSpeedLimit;      	// Pit speed limit in kilometres per hour
    short   gamePaused;             // Whether the game is paused
    short   isSpectating;        	// Whether the player is spectating
    short   spectatorCarIndex;  	// Index of the car being spectated
    short   sliProNativeSupport;	// SLI Pro support, 0 = inactive, 1 = active
    short   numMarshalZones;        // Number of marshal zones to follow

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


    public String getWeather(){
        switch(weather){
            case 0: return "Clear";
            case 1: return "Light Cloud";
            case 2: return "Overcast";
            case 3: return "Light Rain";
            case 4: return "Heavy Rain";
            case 5: return "Storm";
            default: return "";
        }
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
            case 4: return "Practice";
            case 5: return "Q1";
            case 6: return "Q2";
            case 7: return "Q3";
            case 8: return "Qualifying";
            case 9: return "OSQ";
            case 10: return "Race";
            case 11: return "R2";
            case 12: return "Time Trial";
            default: return "";
        }
    }


    public String getFormula(){
        switch(formula){
            case 0: return "Modern";
            case 1: return "Classic";
            case 2: return "F2";
            case 3: return "F1 Generic";
            default: return "";
        }
    }

    public String getSessionTimeLeft(){
        return printFormattedSeconds(sessionTimeLeft);
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


    @Override
    public String toString(){
        String ret = "Weather: "+getWeather()+"\n";
        ret += "Track Temperature: "+trackTemperature+" ºC\n";
        ret += "Air Temperature: "+airTemperature+" ºC\n";
        ret += "Total laps: "+totalLaps+" laps\n";
        ret += "Track Length: "+trackLength+" m\n";
        ret += "Session Type: "+getSessionType()+"\n";
        ret += "Track: "+getTrack()+"\n";
        ret += "Era: "+getFormula()+"\n";
        ret += "Session Time Left: "+getSessionTimeLeft()+"\n";
        ret += "Session Duration: "+getSessionDuration()+"\n";
        ret += "Pit Speed Limit: "+pitSpeedLimit+"\n";
        ret += "Game paused: "+gamePaused+"\n";
        ret += "Spectator Car Index: "+  spectatorCarIndex+"\n";
        ret += "Marshal Zones: "+ numMarshalZones+"\n";

        return ret;
    }
}