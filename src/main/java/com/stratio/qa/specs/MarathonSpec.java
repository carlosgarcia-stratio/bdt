package com.stratio.qa.specs;

import com.stratio.qa.utils.ThreadProperty;
import com.stratio.qa.clients.marathon.MarathonClient;

import cucumber.api.java.en.When;
import mesosphere.marathon.client.Marathon;
import mesosphere.marathon.client.model.v2.Task;
import org.assertj.core.api.Assertions;

import java.util.List;

public class MarathonSpec extends BaseGSpec {

    /**
     * Generic constructor.
     *
     * @param spec object
     */
    public MarathonSpec(CommonG spec) {
        this.commonspec = spec;
    }

    @When("^I get host ip for task ( with appId '(.+?)')?( and save the value in environment variable '(.+?)')?$")
    public void getMarathonTaskId(String appId, String envVar) {

        Marathon client = getMarathonClient();

        List<Task> tasks = client.getAppTasks(appId).getTasks();
        Task task = tasks.get(0);
        ThreadProperty.set(envVar, task.getHost());
    }

    private Marathon getMarathonClient() {

        String endpoint = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/marathon");
        String token = ThreadProperty.get("dcosAuthCookie");

        Marathon client = MarathonClient.getInstance(endpoint, token);
        Assertions.assertThat(client).as("Marathon client not initialized").isNotEqualTo(null);

        return client;
    }

}
