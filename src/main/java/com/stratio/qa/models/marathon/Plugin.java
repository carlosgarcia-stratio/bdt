package com.stratio.qa.models.marathon;

import java.util.List;

public class Plugin {

    private String id;

    private String implementation;

    private Info info;

    private String plugin;

    private List<String> tags;

    public String getId() {
        return id;
    }

    public String getImplementation() {
        return implementation;
    }

    public Info getInfo() {
        return info;
    }

    public String getPlugin() {
        return plugin;
    }

    public List<String> getTags() {
        return tags;
    }

    public static class Info {

        private String version;

        private List<Object> array;

        private Boolean test;

        public String getVersion() {
            return version;
        }

        public List<Object> getArray() {
            return array;
        }

        public Boolean getTest() {
            return test;
        }
    }
}
