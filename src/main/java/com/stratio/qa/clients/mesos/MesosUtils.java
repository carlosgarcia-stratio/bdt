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

import com.stratio.qa.models.mesos.MesosTask;
import java.util.Comparator;
import java.util.Map;

public class MesosUtils {

    MesosApiClient mesosApiClient;

    public MesosUtils(MesosApiClient client) {
        this.mesosApiClient = client;
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

