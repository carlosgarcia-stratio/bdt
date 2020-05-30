/*
 * Copyright (C) 2014 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
