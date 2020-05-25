package com.stratio.qa.models.cct.configurationApi;

import java.util.ArrayList;

public class CalicoPolicy {

    Integer order;

    String action;

    ArrayList<String> portList;

    ArrayList<String> netList;

    String metadataKey;

    String operator;

    String[] metadataValue;

    String protocol;

    Integer icmpType;

    Integer icmpCode;

    public Integer getOrder() {
        return order;
    }

    public String getAction() {
        return action;
    }

    public ArrayList<String> getPortList() {
        return portList;
    }

    public ArrayList<String> getNetList() {
        return netList;
    }

    public String getMetadataKey() {
        return metadataKey;
    }

    public String getOperator() {
        return operator;
    }

    public String[] getMetadataValue() {
        return metadataValue;
    }

    public String getProtocol() {
        return protocol;
    }

    public Integer getIcmpType() {
        return icmpType;
    }

    public Integer getIcmpCode() {
        return icmpCode;
    }
}
