package com.stratio.qa.clients.marathon;

import com.stratio.qa.models.marathon.AppResponse;
import com.stratio.qa.models.marathon.Task;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class MarathonUtils {

    MarathonApiClient client;

    public MarathonUtils(MarathonApiClient client) {
        this.client = client;
    }

    public String getTaskId(String taskName, String serviceId) throws Exception {
        AppResponse app = this.client.getApp(serviceId);
        Collection<Task> tasks = app.getApp().getTasks();

        Task task = tasks.stream().filter(t -> t.getId().matches(taskName)).findFirst().orElse(null);
        assertThat(task).as("Task " + taskName + " not found in Marathon for service " + serviceId).isNotNull();

        return task.getId();
    }
}
