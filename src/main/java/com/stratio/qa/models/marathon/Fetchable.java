package com.stratio.qa.models.marathon;

public class Fetchable {

    private String uri;

    private Boolean executable;

    private Boolean extract;

    private Boolean cache;

    private String destPath;

    public String getUri() {
        return uri;
    }

    public Boolean getExecutable() {
        return executable;
    }

    public Boolean getExtract() {
        return extract;
    }

    public Boolean getCache() {
        return cache;
    }

    public String getDestPath() {
        return destPath;
    }
}
