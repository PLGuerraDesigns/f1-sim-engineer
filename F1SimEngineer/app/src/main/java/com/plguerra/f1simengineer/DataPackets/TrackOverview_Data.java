package com.plguerra.f1simengineer.DataPackets;

public class TrackOverview_Data {

    short   trackId;         		// -1 for unknown, 0-21 for tracks, see appendix


    public TrackOverview_Data(int i)
    {
        trackId = (short) i;
    }

    public String getTrackImage() {
        switch(trackId) {
            case 0: return "track_melbourne";
            case 1: return "track_paulricard";
            case 2: return "track_shanghai";
            case 3: return "track_catalunya";
            case 4: return "track_monaco";
            case 5: return "track_montreal";
            case 6: return "track_silverstone";
            case 7: return "track_hockenheim";
            case 8: return "track_hungaroring";
            case 9: return "track_spa";
            case 10: return "track_monza";
            case 11: return "track_singapore";
            case 12: return "track_suzuka";
            case 13: return "track_abudhabi";
            case 14: return "track_texas";
            case 15: return "track_brazil";
            case 16: return "track_austria";
            case 17: return "track_sochi";
            case 18: return "track_mexico";
            default: return "";
        }
    }

    public String getTrack(){
        switch(trackId){
            case 0: return "Melbourne";
            case 1: return "Paul Ricard";
            case 2: return "Shanghai";
            case 3: return "Catalunya";
            case 4: return "Monaco";
            case 5: return "Montreal";
            case 6: return "Silverstone";
            case 7: return "Hockenheim";
            case 8: return "Hungaroring";
            case 9: return "Spa";
            case 10: return "Monza";
            case 11: return "Singapore";
            case 12: return "Suzuka";
            case 13: return "Abu Dhabi";
            case 14: return "Texas";
            case 15: return "Brazil";
            case 16: return "Austria";
            case 17: return "Sochi";
            case 18: return "Mexico";
            default: return "";
        }
    }
}
