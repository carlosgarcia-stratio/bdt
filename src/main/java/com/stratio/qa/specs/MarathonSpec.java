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

package com.stratio.qa.specs;

import com.stratio.qa.clients.marathon.MarathonApiClient;
import com.stratio.qa.models.marathon.AppResponse;
import com.stratio.qa.models.marathon.MarathonConstants;
import com.stratio.qa.models.marathon.Task;

import static org.assertj.core.api.Assertions.assertThat;

import cucumber.api.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class MarathonSpec extends BaseGSpec {

    private final Logger logger = LoggerFactory.getLogger(CCTSpec.class);

    private MarathonApiClient marathonApiClient;

    public MarathonSpec(CommonG spec) {
        this.commonspec = spec;
        marathonApiClient = MarathonApiClient.getInstance(this.commonspec);
    }

    @Then("^service with id '(.*)' has '(\\d+)' task[s]? in '(running|finished|failed|staging|starting|killed)' state in Marathon$")
    public void checkNumberOfTasksState(String appId, int numberOfTasks, String state) throws Exception {
        AppResponse app = marathonApiClient.getApp(appId);
        Collection<Task> tasks = app.getApp().getTasks();

        String translatedState = MarathonConstants.statesDict.get(state);
        int count = (int) tasks.stream().filter(task -> task.getState().equals(translatedState)).count();

        assertThat(count)
                .as("Number of task in state " + translatedState + " for service " + appId + " does not match.")
                .isEqualTo(numberOfTasks);
    }

    @Then("^service with id '(.*)' has all tasks in '(running|finished|failed|staging|starting|killed)' state in Marathon$")
    public void checkAllTasksState(String appId, String state) throws Exception {
        AppResponse app = marathonApiClient.getApp(appId);
        Collection<Task> tasks = app.getApp().getTasks();

        String translatedState = MarathonConstants.statesDict.get(state);
        int count = (int) tasks.stream().filter(task -> task.getState().equals(translatedState)).count();

        assertThat(count)
                .as("Number of task in state " + translatedState + " for service " + appId + " does not match.")
                .isEqualTo(tasks.size());
    }

}