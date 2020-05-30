package com.stratio.qa.models.mesos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stratio.qa.models.utils.UnixTimestampDeserializer;

import java.util.Date;
import java.util.Map;

public class TaskStatus {

    private String state;

    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private Date timestamp;

    @JsonProperty("container_status")
    private Map<String, Object> containerStatus;

    private boolean healthy;

    public String getState() {
        return state;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Map<String, Object> getContainerStatus() {
        return containerStatus;
    }

    public boolean isHealthy() {
        return healthy;
    }
}
