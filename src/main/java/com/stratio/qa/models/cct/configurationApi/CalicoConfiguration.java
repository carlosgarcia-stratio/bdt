package com.stratio.qa.models.cct.configurationApi;

import com.stratio.qa.models.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class CalicoConfiguration extends BaseResponse {

    String networkName;

    String ipPool;

    ArrayList<CalicoPolicy> ingress;

    ArrayList<CalicoPolicy> egress;

    List<String> types;

    boolean validIpPool;

    int runningContainers;

    public String getNetworkName() {
        return networkName;
    }

    public String getIpPool() {
        return ipPool;
    }

    public ArrayList<CalicoPolicy> getIngress() {
        return ingress;
    }

    public ArrayList<CalicoPolicy> getEgress() {
        return egress;
    }

    public List<String> getTypes() {
        return types;
    }

    public int getRunningContainers() {
        return runningContainers;
    }

    public boolean isValidIpPool() {
        return validIpPool;
    }
}
