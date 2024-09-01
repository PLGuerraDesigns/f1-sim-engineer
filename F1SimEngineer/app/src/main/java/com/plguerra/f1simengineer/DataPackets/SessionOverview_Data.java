package com.plguerra.f1simengineer.DataPackets;

import java.util.ArrayList;
import java.util.Date;

public class SessionOverview_Data {

    /**
     * This is just mock data to be used until we get a database so we can still do the ux development.
     */
    short   trackId;         		// -1 for unknown, 0-21 for tracks, see appendix
    ArrayList<SessionOverviewSingle_Data> Sessions;

    public SessionOverview_Data(int i)
    {
        trackId = (short) i;
    }


    private ArrayList<SessionOverviewSingle_Data> mockDatabaseCall(int _trackId) {
        switch(trackId) {
            case 0:
                return makeMockData();
                default: return new ArrayList<SessionOverviewSingle_Data>();
        }
    }

    private ArrayList<SessionOverviewSingle_Data> makeMockData() {

            ArrayList<SessionOverviewSingle_Data> testArray = new ArrayList<>();
            Date datetest = new Date();
            SessionOverviewSingle_Data testData1 = new SessionOverviewSingle_Data("Practice", datetest, "Ferrari",
                    15, 3, "S");
        SessionOverviewSingle_Data testData2 = new SessionOverviewSingle_Data("Practice", datetest, "Ferrari",
                10, 4, "M");
        testArray.add(testData1);
        testArray.add(testData2);
        return testArray;
        }
}
