package com.stratio.qa.models.marathon;

import java.util.Collection;

public class Group {

    private String id;

    private Collection<App> apps;

    private Collection<Group> groups;

    private Collection<String> dependencies;

    private String version;

    public String getId() {
        return id;
    }

    public Collection<App> getApps() {
        return apps;
    }

    public Collection<Group> getGroups() {
        return groups;
    }

    public Collection<String> getDependencies() {
        return dependencies;
    }

    public String getVersion() {
        return version;
    }
}
