package com.stratio.qa.models.cct.deployApi;

public class TaskResources {

    private Double disk;

    private Double mem;

    private Double gpus;

    private Double cpus;

    private String ports;

    public Double getDisk() {
        return disk;
    }

    public void setDisk(Double disk) {
        this.disk = disk;
    }

    public Double getMem() {
        return mem;
    }

    public void setMem(Double mem) {
        this.mem = mem;
    }

    public Double getGpus() {
        return gpus;
    }

    public void setGpus(Double gpus) {
        this.gpus = gpus;
    }

    public Double getCpus() {
        return cpus;
    }

    public void setCpus(Double cpus) {
        this.cpus = cpus;
    }

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }
}
