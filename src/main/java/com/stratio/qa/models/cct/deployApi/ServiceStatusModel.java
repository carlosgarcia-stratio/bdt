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

package com.stratio.qa.models.cct.deployApi;

import com.stratio.qa.models.BaseResponse;

import java.util.List;

public class ServiceStatusModel extends BaseResponse {

    private String serviceName;

    private String service;

    private String model;

    private int status;

    private int healthy;

    private List<String> actions;

    public String getServiceName() {
        return serviceName;
    }

    public String getService() {
        return service;
    }

    public String getModel() {
        return model;
    }


    public int getStatus() {
        return status;
    }

    public int getHealthy() {
        return healthy;
    }

    public List<String> getActions() {
        return actions;
    }
}
