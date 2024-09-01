package com.plguerra.f1simengineer.DataPackets;

import java.util.Date;

public class SessionOverviewSingle_Data {
    /**
     * This is just mock data to be used until we get a database so we can still do the ux development.
     */
    String  sessionType;
    Date sessionDate;
    String  sessionVehicle;
    int   sessionAmountOfLaps;
    int   sessionPosition;
    String  sessionTireType;

    public SessionOverviewSingle_Data(String _sessionType, Date _sessionDate, String _sessionVehicle,
                                int _sessionAmountOfLaps, int _sessionPosition, String _sessionTireType) {
        sessionType = _sessionType;
        sessionDate = _sessionDate;
        sessionVehicle = _sessionVehicle;
        sessionAmountOfLaps = _sessionAmountOfLaps;
        sessionPosition = _sessionPosition;
        sessionTireType = _sessionTireType;
    }
}
