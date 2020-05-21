package com.stratio.qa.models.mesos;

public class TaskResources {

    private Double disk;

    private Double mem;

    private Double gpus;

    private Double cpus;

    private String ports;

    public Double getDisk() {
        return disk;
    }

    public Double getMem() {
        return mem;
    }

    public Double getGpus() {
        return gpus;
    }

    public Double getCpus() {
        return cpus;
    }

    public String getPorts() {
        return ports;
    }
}