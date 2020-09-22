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

package com.stratio.qa.clients.marathon;

import com.stratio.qa.models.marathon.AppResponse;
import com.stratio.qa.models.marathon.Task;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class MarathonUtils {

    private static MarathonApiClient marathonApiClient;

    public MarathonUtils(MarathonApiClient client) {
        this.marathonApiClient = client;
    }

    public String getTaskId(String taskName, String serviceId) throws Exception {
        AppResponse app = marathonApiClient.getApp(serviceId);
        Collection<Task> tasks = app.getApp().getTasks();

        Task task = tasks.stream().filter(t -> t.getId().matches(taskName)).findFirst().orElse(null);
        assertThat(task).as("Task " + taskName + " not found in Marathon for service " + serviceId).isNotNull();

        return task.getId();
    }
}

