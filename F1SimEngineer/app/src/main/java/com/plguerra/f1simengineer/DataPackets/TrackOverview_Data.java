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
            case 1: return "track_paulrichard";
            case 2: return "track_shanghai";
            case 3: return "track_melbourne";
            case 4: return "track_catalunya";
            case 5: return "track_monaco";
            case 6: return "track_montreal";
            case 7: return "track_silverstone";
            case 8: return "track_hockenheim";
            case 9: return "track_hungaroring";
            case 10: return "track_spa";
            case 11: return "track_monza";
            case 12: return "track_singapore";
            case 13: return "track_suzuka";
            case 14: return "track_abudhabi";
            case 15: return "track_texas";
            case 16: return "track_brazil";
            case 17: return "track_austria";
            case 18: return "track_sochi";
            case 19: return "track_mexico";
            case 20: return "track_melbourne";
            case 21: return "track_melbourne";
            case 22: return "track_melbourne";
            case 23: return "track_melbourne";
            case 24: return "track_melbourne";
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
}
