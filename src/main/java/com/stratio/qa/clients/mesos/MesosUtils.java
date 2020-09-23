package com.stratio.qa.clients.mesos;

import com.stratio.qa.models.mesos.MesosTask;

import java.util.Comparator;
import java.util.Map;

public class MesosUtils {

    MesosApiClient client;

    public MesosUtils(MesosApiClient client) {
        this.client = client;
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
