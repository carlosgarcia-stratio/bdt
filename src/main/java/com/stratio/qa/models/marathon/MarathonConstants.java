package com.stratio.qa.models.marathon;

import java.util.HashMap;
import java.util.Map;

public class MarathonConstants {

    public static Map<String, String> statesDict = new HashMap<String, String>() { {
            put("running", "TASK_RUNNING");
            put("failed", "TASK_FAILED");
            put("finished", "TASK_FINISHED");
            put("staging", "TASK_STAGING");
            put("starting", "TASK_STARTING");
            put("killed", "TASK_KILLED");
        } };


}
