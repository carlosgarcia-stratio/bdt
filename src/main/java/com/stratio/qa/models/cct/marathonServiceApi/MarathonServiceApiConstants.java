package com.stratio.qa.models.cct.marathonServiceApi;

import java.util.HashMap;
import java.util.Map;

public class MarathonServiceApiConstants {

    public static Map<String, String> statusDict = new HashMap<String, String>() { {
            put("running", "RUNNING");
            put("failed", "FAILED");
            put("finished", "FINISHED");
            put("staging", "STAGING");
            put("starting", "STARTING");
            put("killed", "KILLED");
        } };


}
