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
