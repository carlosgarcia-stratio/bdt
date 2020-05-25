package com.stratio.qa.models.cct.marathonServiceApi;

import java.math.BigDecimal;

public class Resources {

    private BigDecimal disk;

    private BigDecimal mem;

    public BigDecimal getDisk() {
        return disk;
    }

    public BigDecimal getMem() {
        return mem;
    }

    public BigDecimal getGpus() {
        return gpus;
    }

    public BigDecimal getCpus() {
        return cpus;
    }

    public String getPorts() {
        return ports;
    }

    private BigDecimal gpus;

    private BigDecimal cpus;

    private String ports;

}