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

package com.stratio.qa.clients.mesos;

import com.ning.http.client.Response;
import com.stratio.qa.clients.BaseClient;
import com.stratio.qa.models.mesos.Log;
import com.stratio.qa.models.mesos.MesosStateSummary;
import com.stratio.qa.models.mesos.MesosTask;
import com.stratio.qa.models.mesos.MesosTasksResponse;
import com.stratio.qa.specs.CommonG;
import com.stratio.qa.utils.ThreadProperty;

import java.util.Comparator;
import java.util.Map;

public class MesosApiClient extends BaseClient {

    private static MesosApiClient CLIENT;

    public static MesosUtils utils;

    public static MesosApiClient getInstance(CommonG common) {
        if (CLIENT == null) {
            CLIENT = new MesosApiClient(common);
            utils = new MesosUtils(CLIENT);
        }
        return CLIENT;
    }

    private MesosApiClient(CommonG common) {
        super(common);
    }

    public Log getLogs(String path, String logType, Map<String, String> queryParams) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":" + getPort());
        String endpoint = url.concat(path).concat(logType);

        if (queryParams != null) {
            for (Map.Entry<String, String> param: queryParams.entrySet()) {
                endpoint = endpoint.concat("&").concat(param.getKey()).concat("=").concat(param.getValue());
            }
        }

        Response response = get(endpoint);
        return map(response, Log.class);
    }

    public MesosStateSummary getStateSummary() throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":" + getPort()).concat("/mesos/state-summary");

        Response response = get(url);
        return map(response, MesosStateSummary.class);
    }

    public MesosTasksResponse getMesosTask(String taskId) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":" + getPort()).concat("/mesos/tasks?task_id=");
        url = url.concat(taskId);

        Response response = get(url);
        return map(response, MesosTasksResponse.class);
    }

    public String getMesosTaskContainerId(MesosTask task) {
        return task.getStatuses().stream()
                .sorted(Comparator.comparing(com.stratio.qa.models.mesos.TaskStatus::getTimestamp,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .map(com.stratio.qa.models.mesos.TaskStatus::getContainerStatus)
                .filter(status -> status.containsKey("container_id"))
                .map(status -> (Map<String, Object>) status.get("container_id"))
                .filter(container -> container.containsKey("value"))
                .map(container -> String.valueOf(container.get("value")))
                .findFirst()
                .orElse(null);
    }
}
