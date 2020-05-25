package com.stratio.qa.models.marathon;

public class QueueElement {

    private int count;

    private Delay delay;

    private VersionedApp app;

    public int getCount() {
        return count;
    }

    public Delay getDelay() {
        return delay;
    }

    public VersionedApp getApp() {
        return app;
    }
}
