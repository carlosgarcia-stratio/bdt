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
