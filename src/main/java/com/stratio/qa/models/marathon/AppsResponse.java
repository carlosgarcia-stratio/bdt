package com.stratio.qa.models.marathon;

import java.util.List;

public class AppsResponse extends VersionedApp {

    private List<VersionedApp> apps;

    public List<VersionedApp> getApps() {
        return apps;
    }
}
