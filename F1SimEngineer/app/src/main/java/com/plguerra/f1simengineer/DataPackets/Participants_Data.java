package com.plguerra.f1simengineer.DataPackets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Participants_Data {
    public static int SIZE = 54;

    public short    aiControlled;           // Whether the vehicle is AI (1) or Human (0) controlled
    public short    driverId;		        // Driver id - see appendix
    public short    teamId;                 // Team id - see appendix
    public short    raceNumber;             // Race number of the car
    public short    nationality;            // Nationality of the driver

    public Participants_Data(byte[] content){
        ByteBuffer bb = ByteBuffer.wrap(content);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        aiControlled = bb.get();
        driverId = bb.get();
        teamId = bb.get();
        raceNumber = bb.get();
        nationality = bb.get();
    }

    public String getTeam(){
        switch(teamId){
            case 0: return "Mercedes";
            case 1: return "Ferrari";
            case 2: return "RedBull";
            case 3: return "Williams";
            case 4: return "Racing Point";
            case 5: return "Renault";
            case 6: return "Toro Rosso";
            case 7: return "Haas";
            case 8: return "McLaren";
            case 9: return "Alfa Romeo";
            case 10: return "McLaren '88";
            case 11: return "McLaren '91";
            case 12: return "Williams '92";
            case 13: return "Ferrari '95";
            case 14: return "Williams '96";
            case 15: return "McLaren '98";
            case 16: return "Ferrari '02";
            case 17: return "Ferarri '04";
            case 18: return "Renault '06";
            case 19: return "Ferrari '07";
            case 20: return "McLaren '08";
            case 21: return "RedBull '10";
            case 22: return "Ferarri '76";
            case 23: return "ART Grand Prix";
            case 24: return "Campos Vexatec Racing";
            case 25: return "Carlin";
            case 26: return "Charouz Racing System";
            case 27: return "DAMS";
            case 28: return "Russian Time";
            case 29: return "MP Motorsport";
            case 30: return "Pertamina";
            case 31: return "McLaren '90";
            case 32: return "Trident";
            case 33: return "BWT Arden";
            case 34: return "McLaren '76";
            case 35: return "Lotus '72";
            case 36: return "Ferrari '79";
            case 37: return "McLaren '82";
            case 38: return "Williams 2003";
            case 39: return "Brawn GP 2009";
            case 40: return "Lotus '78";
            case 42: return "Art GP '19";
            case 43: return "Campos '19";
            case 44: return "Carlin '19";
            case 45: return "Sauber Junior Charouz '19";
            case 46: return "Dams '19";
            case 47: return "Uni-Virtuosi '19";
            case 48: return "MP Motorsport '19";
            case 49: return "Prema '19";
            case 50: return "Trident '19";
            case 51: return "Arden '19";
            case 63: return "Ferrari '90";
            case 64: return " McLaren '10";
            case 65: return "Ferrari '10";
            default: return "-";
        }
    }
}
