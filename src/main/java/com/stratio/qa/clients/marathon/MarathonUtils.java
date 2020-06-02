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

        Task task = tasks.stream().filter(t -> t.getId().matches(taskName)).findFirst().orElseGet(null);
        assertThat(task).as("Task " + taskName + " not found in Marathon for service " + serviceId).isNotNull();

        return task.getId();
    }
}
