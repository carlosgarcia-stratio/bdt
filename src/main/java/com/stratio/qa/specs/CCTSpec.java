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

import com.ning.http.client.Response;
import com.stratio.qa.assertions.Assertions;
import com.stratio.qa.clients.cct.MarathonServicesApi.CctMarathonServiceApiClient;
import com.stratio.qa.clients.cct.deployApi.DeployApiClient;
import com.stratio.qa.clients.mesos.MesosApiClient;
import com.stratio.qa.models.BaseResponse;
import com.stratio.qa.models.cct.deployApi.*;
import com.stratio.qa.models.cct.marathonServiceApi.*;
import com.stratio.qa.models.mesos.Log;
import com.stratio.qa.models.mesos.MesosStateSummary;
import com.stratio.qa.utils.ThreadProperty;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Future;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.fail;

/**
 * Generic Command Center Specs.
 *
 * @see <a href="CCTSpec-annotations.html">Command Center Steps</a>
 */
public class CCTSpec extends BaseGSpec {

    private final Logger logger = LoggerFactory.getLogger(CCTSpec.class);

    RestSpec restSpec;

    private DeployApiClient deployApiClient;

    private CctMarathonServiceApiClient marathonServiceApiClient;

    private MesosApiClient mesosApiClient;

    /**
     * Generic constructor.
     *
     * @param spec object
     */
    public CCTSpec(CommonG spec) {
        this.commonspec = spec;
        restSpec = new RestSpec(spec);
        deployApiClient = DeployApiClient.getInstance(this.commonspec);
        marathonServiceApiClient = CctMarathonServiceApiClient.getInstance(this.commonspec);
        mesosApiClient = MesosApiClient.getInstance(this.commonspec);
    }

    @When("^I get host ip for task '(.+?)' in service with id '(.+?)' and save the value in environment variable '(.+?)'$")
    public void getMarathonTaskId(String taskName, String serviceId, String envVar) throws Exception {
        if (ThreadProperty.get("cct-marathon-services_id") == null) {
            setHostFromDeployApi(taskName, serviceId, envVar);
        } else {
            setHostFromMarathonServiceApi(taskName, serviceId, envVar);
        }
    }

    private void setHostFromDeployApi(String taskName, String appId, String envVar) throws Exception {
        DeployedTask task = getTaskFromDeployApi(taskName, appId);
        assertThat(task).as("No task " + taskName + " found for cct service " + appId).isNotEqualTo(null);

        String host = task.getHost();
        ThreadProperty.set(envVar, host);
    }

    private DeployedTask getTaskFromDeployApi(String taskName, String appId) throws Exception {
        deployApiClient.withContext(this.commonspec);

        DeployedApp app = deployApiClient.getDeployedApp(appId);
        assertThat(app).as("Error retrieving deploy-api service " + appId).isNotEqualTo(null);

        return app.getTasks().stream()
                .filter(task -> task.getState().equals(TaskStatus.RUNNING.toString()))
                .filter(task -> task.getName().equals(taskName))
                .findFirst().orElse(null);
    }

    private void setHostFromMarathonServiceApi(String taskName, String serviceId, String envVar) throws Exception {
        DeployedServiceTask task = getTaskFromMarathonServiceApi(taskName, serviceId);
        assertThat(task).as("No task " + taskName + " found for cct marathon service " + serviceId).isNotEqualTo(null);

        String host = task.getHost();
        ThreadProperty.set(envVar, host);
    }

    private DeployedServiceTask getTaskFromMarathonServiceApi(String taskName, String serviceId) throws Exception {
        marathonServiceApiClient.withContext(this.commonspec);

        DeployedService service = marathonServiceApiClient.getService(serviceId);
        assertThat(service).as("Error retrieving cct service " + serviceId).isNotEqualTo(null);

        return service.getTasks().stream()
                .filter(task -> task.getStatus().equals(TaskStatus.RUNNING))
                .filter(task -> task.getName().equals(taskName))
                .findFirst().orElse(null);
    }


    @Given("^in less than '(\\d+)' seconds, checking each '(\\d+)' seconds,The '(stdout|stderr)' of service '(.+?)'( with task type '(.+?)')? contains '(.+?)'$")
    public void readLogsInLessEachFromService(Integer timeout, Integer wait, String logType, String service, String taskType, String logToCheck) throws Exception {

        List<String> ids;
        if (ThreadProperty.get("cct-marathon-services_id") == null) {
            ids = getTaskIdsFromDeployApi(service, taskType);
        } else {
            ids = getTaskIdsFromMarathonServiceApi(service, taskType);
        }

        int time = 0;
        Long defaultLength = 500000L;
        boolean found = false;
        while (!(time >= timeout || found)) {
            for (String taskId: ids) {
                String path;
                if (ThreadProperty.get("cct-marathon-services_id") == null) {
                    path = getLogPathFromDeployApi(taskId, "READ", logType);
                } else {
                    path = getLogPathFromMarathonServiceApi(taskId, "READ", logType);
                }

                Long offset = getLogOffsetFromPath(path, logType);
                String data = getLogDataFromPath(path, logType, offset, defaultLength, 10);

                String fileOutputName = taskId.concat("-").concat(Integer.toString(time));
                Files.write(Paths.get(System.getProperty("user.dir") + "/target/test-classes/" + fileOutputName), data.getBytes());
                if (data.contains(logToCheck)) {
                    found = true;
                    break;
                }
            }
            Thread.sleep(wait * 1000);
            time += wait;
        }

        assertThat(found).as(logToCheck + " not found in " + logType + " for service " + service).isTrue();
    }

    @Given("^I want to download '(stdout|stderr)' last '(\\d+)' lines of service '(.+?)'( with task type '(.+?)')?")
    public void downLoadLogsFromService(String logType, Integer lastLinesToRead, String service, String taskType) throws Exception {
        List<String> ids;
        if (ThreadProperty.get("cct-marathon-services_id") == null) {
            ids = getTaskIdsFromDeployApi(service, taskType);
        } else {
            ids = getTaskIdsFromMarathonServiceApi(service, taskType);
        }

        for (String taskId: ids) {
            String path;
            if (ThreadProperty.get("cct-marathon-services_id") == null) {
                path = getLogPathFromDeployApi(taskId, "READ", logType);
            } else {
                path = getLogPathFromMarathonServiceApi(taskId, "READ", logType);
            }

            Long offset = getLogOffsetFromPath(path, logType);
            String data = getLastLinesLogDataFromPath(path, logType, offset, lastLinesToRead);

            String fileOutputName = taskId.concat("-mesos.log");
            Files.write(Paths.get(System.getProperty("user.dir") + "/target/test-classes/" + fileOutputName), data.getBytes());
        }
    }

    @Given("^The '(stdout|stderr)' of service '(.+?)'( with task type '(.+?)')? contains '(.+?)' in the last '(\\d+)' lines$")
    public void checkInLastLinesLogsFromService(String logType, String service, String taskType, String logToCheck, Integer lastLinesToRead) throws Exception {
        List<String> ids;
        if (ThreadProperty.get("cct-marathon-services_id") == null) {
            ids = getTaskIdsFromDeployApi(service, taskType);
        } else {
            ids = getTaskIdsFromMarathonServiceApi(service, taskType);
        }

        boolean found = false;
        for (String taskId: ids) {
            String path;
            if (ThreadProperty.get("cct-marathon-services_id") == null) {
                path = getLogPathFromDeployApi(taskId, "READ", logType);
            } else {
                path = getLogPathFromMarathonServiceApi(taskId, "READ", logType);
            }

            Long offset = getLogOffsetFromPath(path, logType);
            String data = getLastLinesLogDataFromPath(path, logType, offset, lastLinesToRead);

            found = data.contains(logToCheck);
            if (found) {
                break;
            }
        }

        assertThat(found).as("Last " + lastLinesToRead + " lines of log for service task " + taskType + " do not contain " + logToCheck);
    }

    private List<String> getTaskIdsFromDeployApi(String appId, String taskName) throws Exception {
        deployApiClient.withContext(this.commonspec);

        DeployedApp app = deployApiClient.getDeployedApp(appId);
        assertThat(app).as("Error retrieving deploy-api service " + appId).isNotEqualTo(null);

        String regex_name = taskName == null ? ".*" : ".*" + taskName + ".*";

        return app.getTasks().stream()
                .filter(task -> (task.getState().equals(ServiceStatus.RUNNING.toString()) || task.getState().equals(ServiceStatus.UNKNOWN.toString())))
                .filter(task -> task.getName().matches(regex_name))
                .map(DeployedTask::getId).collect(Collectors.toList());
    }


    private List<String> getTaskIdsFromMarathonServiceApi(String serviceId, String taskName) throws Exception {
        marathonServiceApiClient.withContext(this.commonspec);

        DeployedService service = marathonServiceApiClient.getService(serviceId);
        assertThat(service).as("Error retrieving cct service " + serviceId).isNotEqualTo(null);

        String regex_name = taskName == null ? ".*" : ".*" + taskName + ".*";

        return service.getTasks().stream()
                .filter(task -> task.getStatus().equals(TaskStatus.RUNNING))
                .filter(task -> task.getName().matches(regex_name))
                .map(DeployedServiceTask::getId).collect(Collectors.toList());
    }

    private String getLogPathFromDeployApi(String taskId, String logAction, String logName) throws Exception {
        deployApiClient.withContext(this.commonspec);

        List<SandboxItem> logPathsResponse = deployApiClient.getLogPaths(taskId).getList();
        assertThat(logPathsResponse).as("Error retrieving logs for deploy-api task " + taskId).isNotEqualTo(null);

        return logPathsResponse.stream()
                .filter(log -> log.getName().equals("null"))
                .filter(log -> log.getAction().toString().equals(logAction))
                .map(SandboxItem::getPath)
                .findFirst().orElse(null);
    }

    private String getLogPathFromMarathonServiceApi(String taskId, String logAction, String logName) throws Exception {
        marathonServiceApiClient.withContext(this.commonspec);

        TaskLogsResponse logPathsResponse = marathonServiceApiClient.getLogPaths(taskId);
        assertThat(logPathsResponse).as("Error retrieving logs for cct task " + taskId).isNotEqualTo(null);

        return logPathsResponse.getContent().stream()
                .filter(log -> log.getName().equals(logName))
                .filter(log -> log.getAction().toString().equals(logAction))
                .map(TaskLog::getPath)
                .findFirst().orElse(null);
    }

    private Log getLogFromPath(String path, String logType, Map<String, String> queryParams) throws Exception {
        mesosApiClient.withContext(this.commonspec);
        return mesosApiClient.getLogs(path, logType, queryParams);
    }

    private Long getLogOffsetFromPath(String path, String logType) throws Exception {
        mesosApiClient.withContext(this.commonspec);
        return mesosApiClient.getLogs(path, logType, null).getOffset();
    }

    private String getLogDataFromPath(String path, String logType, Long offset, Long length, int chunks) throws Exception {
        mesosApiClient.withContext(this.commonspec);
        String data = "";
        Map<String, String> queryParams = new HashMap<>();
        Long currentOffset = Math.max(offset - chunks * length, 0);

        queryParams.put("offset", currentOffset.toString());
        queryParams.put("length", length.toString());

        for (int i = 1; i <= chunks; i++) {
            data = data.concat(mesosApiClient.getLogs(path, logType, queryParams).getData());
            currentOffset = currentOffset + i * length;
            queryParams.put("offset", offset.toString());
        }
        return data;
    }

    private String getLastLinesLogDataFromPath(String path, String logType, Long offset, int lastLinesToRead) throws Exception {
        mesosApiClient.withContext(this.commonspec);
        String data = "";
        Map<String, String> queryParams = new HashMap<>();

        Long defaultLength = 10000L;
        Long currentOffset = offset;
        queryParams.put("offset", currentOffset.toString());
        queryParams.put("length", defaultLength.toString());

        int linesReaded = 0;
        while (linesReaded < lastLinesToRead) {
            currentOffset -= defaultLength;
            queryParams.put("offset", currentOffset.toString());
            data = mesosApiClient.getLogs(path, logType, queryParams).getData().concat(data);
            linesReaded = data.split("\n").length;
        }
        return data;
    }

    /**
     * TearDown a service with deploy-api
     * @param serviceId
     * @throws Exception
     */
    @Given("^I teardown the service '(.+?)' of tenant '(.+?)'")
    public void tearDownService(String serviceId, String tenantId) throws Exception {

        assertThat(ThreadProperty.get("deploy_api_id")).as("deploy_api_id variable is not set. Check deploy-api is installed and @dcos annotation is working properly").isNotNull();
        deployApiClient.withContext(this.commonspec);

        TearDownResponse response = deployApiClient.tearDown(serviceId);
        assertThat(response).as("Error executing teardown for service: " + serviceId).isNotNull();

        if (ThreadProperty.get("cct-marathon-services_id") == null) {
            checkNotDeployedFromDeployApi(serviceId, 200, 20);
        } else {
            checkNotDeployedFromMarathonServiceApi(serviceId, tenantId, 200, 20);
        }

        checkNotActiveInMesos(serviceId);
    }

    private void checkNotDeployedFromDeployApi(String serviceId, int timeout, int pause) throws Exception {
        List<ServiceStatusModel> services;

        int time = 0;
        while (time < timeout) {
            services = deployApiClient.getDeployedServices().getList();
            if (services.stream().filter(service -> service.getServiceName().equals(serviceId)).count() == 0) {
                return;
            }
            Thread.sleep(pause * 1000);
            time += pause;
        }
        assertThat(false).as("Service " + serviceId + " found in deploy-api deployed services after " + timeout + " seconds").isTrue();
    }

    private void checkNotDeployedFromMarathonServiceApi(String serviceId, String tenantId, int timeout, int pause) throws Exception {
        Collection<DeployedService> services;

        int time = 0;
        while (time < timeout) {
            services = marathonServiceApiClient.getDeployedServices(tenantId).getContent();
            if (services.stream().filter(service -> service.getId().equals(serviceId)).count() == 0) {
                return;
            }
            Thread.sleep(pause * 1000);
            time += pause;
        }
        assertThat(false).as("Service " + serviceId + " found in cct-marathon-service deployed services after " + timeout + " seconds");
    }

    private void checkNotActiveInMesos(String serviceId) throws Exception {
        mesosApiClient.withContext(this.commonspec);

        MesosStateSummary summary = mesosApiClient.getStateSummary();

        String serviceName_regex = ".*" + serviceId + ".*";
        long inactive = summary.getFrameworks().stream()
                .filter(framework -> framework.getName().matches(serviceName_regex))
                .filter(framework -> !framework.isActive())
                .count();

        assertThat(inactive).as("There are inactive frameworks with name " + serviceName_regex).isEqualTo(0);
    }

    /**
     * Scale service from deploy-api
     * @param service
     * @param instances
     * @throws Exception
     */
    @Given("^I scale service '(.+?)' to '(\\d+)' instances")
    public void scaleService(String service, Integer instances) throws Exception {
        assertThat(ThreadProperty.get("deploy_api_id")).as("deploy_api_id variable is not set. Check deploy-api is installed and @dcos annotation is working properly").isNotNull();
        deployApiClient.withContext(this.commonspec);
        BaseResponse response = deployApiClient.scale(instances, service);

        assertThat(response.getHttpStatus())
                .as("Unexpected code status " + Integer.toString(response.getHttpStatus()) + " scaling service " + service + " to " + instances.toString() + "instances")
                .isEqualTo(instances.intValue());
    }

    /**
     * Checks in Command Center service status
     * @param timeout
     * @param wait
     * @param service
     * @param numTasks
     * @param taskNameRegex
     * @param expectedStatus Expected status (healthy|unhealthy|running|stopped)
     * @throws Exception
     */
    @Given("^Test in less than '(\\d+)' seconds, checking each '(\\d+)' seconds, I check that the service '(.+?)' in CCT with '(\\d+)' tasks of type '(.+?)' is in '(running|failed|finished|staging|starting)' status")
    public void checkServiceStatusNew(Integer timeout, Integer wait, String service, Integer numTasks, String taskNameRegex, String expectedStatus) throws Exception {

        if (ThreadProperty.get("cct-marathon-services_id") == null) {
            String parsedSate = DeployApiConstants.statesDict.get(expectedStatus);
            checkServiceStatusFromDeployApi(timeout, wait, service, numTasks, taskNameRegex, parsedSate);
        } else {
            String parsedSatus = MarathonServiceApiConstants.statusDict.get(expectedStatus);
            checkNotDeployedFromMarathonServiceApi(timeout, wait, service, numTasks, taskNameRegex, parsedSatus);
        }
    }

    private void checkServiceStatusFromDeployApi(Integer timeout, Integer pause, String serviceId, Integer numTasksToCheck, String taskNameRegex, String expectedStatus) throws Exception {

        int time = 0;
        while (time < timeout) {
            DeployedApp app = deployApiClient.getDeployedApp(serviceId);
            List<DeployedTask> tasks = app.getTasks();

            List<DeployedTask> lastTasks = tasks.stream().collect(
                    Collectors.groupingBy(DeployedTask::getName, Collectors.maxBy(Comparator.comparing(DeployedTask::getTimestamp)))
            ).values().stream().map(Optional::get).collect(Collectors.toList());

            Integer numTasks = new Long(lastTasks.stream()
                    .filter(task -> task.getName().matches(taskNameRegex))
                    .filter(task -> task.getState().equals(expectedStatus))
                    .count()).intValue();

            if (numTasks == numTasksToCheck) {
                return;
            }
            Thread.sleep(pause * 1000);
            time += pause;
        }
        assertThat(false).as("Number of tasks for service " + serviceId + " and task regexp " +
                taskNameRegex + " does not match").isTrue();
    }


    private void checkNotDeployedFromMarathonServiceApi(Integer timeout, Integer pause, String serviceId, Integer numTasksToCheck, String taskNameRegex, String expectedStatus) throws Exception {

        int time = 0;
        Map<String, String> queryParams = new HashMap<String, String>() { {
                put("tpage", "1");
                put("tsize", "500");
            } };
        while (time < timeout) {
            DeployedService service = marathonServiceApiClient.getService(serviceId, queryParams);
            List<DeployedServiceTask> tasks = service.getTasks();

            List<DeployedServiceTask> lastTasks = tasks.stream().collect(
                    Collectors.groupingBy(DeployedServiceTask::getName, Collectors.maxBy(Comparator.comparing(DeployedServiceTask::getTimestamp)))
            ).values().stream().map(Optional::get).collect(Collectors.toList());

            Integer numTasks = new Long(lastTasks.stream()
                    .filter(task -> task.getName().matches(taskNameRegex))
                    .filter(task -> task.getStatus().toString().equals(expectedStatus))
                    .count()).intValue();

            if (numTasks.equals(numTasksToCheck)) {
                return;
            } else {

            }
            Thread.sleep(pause * 1000);
            time += pause;
        }
        assertThat(false).as("Number of tasks for service " + serviceId + " and task regexp " +
                taskNameRegex + " does not match").isTrue();
    }


    @Given("^in less than '(\\d+)' seconds, checking each '(\\d+)' seconds, I check that the service '(.+?)' in CCT with '(\\d+)' tasks of type '(.+?)' is in '(healthy|unhealthy|running|stopped)' status")
    public void checkServiceStatus(Integer timeout, Integer wait, String service, Integer numTasks, String taskType, String expectedStatus) throws Exception {
        String endPoint = "/service/deploy-api/deployments/service?instanceName=" + service;
        if (ThreadProperty.get("cct-marathon-services_id") != null) {
            endPoint = "/service/cct-marathon-services/v1/services/" + service;
        }
        boolean  statusService = false;
        for (int i = 0; (i <= timeout) && (!statusService); i += wait) {
            try {
                Future<Response> response = commonspec.generateRequest("GET", false, null, null, endPoint, "", null);
                commonspec.setResponse(endPoint, response.get());
                statusService = checkServiceStatusInResponse(expectedStatus, commonspec.getResponse().getResponse(), numTasks, taskType);
            } catch (Exception e) {
                commonspec.getLogger().warn("Error in request " + endPoint + " - " + e.toString());
            }
            if (i < timeout) {
                Thread.sleep(wait * 1000);
            }
        }
        if (!statusService) {
            fail(expectedStatus + " status not found after " + timeout + " seconds for service " + service);
        }
    }

    public boolean checkServiceStatusInResponse(String expectedStatus, String response, Integer tasks, String name) {
        JSONObject cctJsonResponse = new JSONObject(response);
        JSONArray arrayOfTasks = (JSONArray) cctJsonResponse.get("tasks");
        int task_counter = 0;
        String key = "status";
        if (arrayOfTasks.getJSONObject(0).toString().contains("state")) {
            key = "state";
            switch (expectedStatus.toLowerCase()) {
                case "running":
                case "healthy":
                    expectedStatus = "TASK_RUNNING";
                    break;
                case "stopped":
                case "unhealthy":
                    expectedStatus = "TASK_KILLED";
                    break;
                default:
                    return false;
            }
        }
        if (arrayOfTasks.length() == 1 || tasks == null) {
            boolean res = (arrayOfTasks.getJSONObject(0).getString(key).equalsIgnoreCase(expectedStatus));
            if (!res) {
                commonspec.getLogger().warn("The status of " + arrayOfTasks.getJSONObject(0).getString("name") + " is " + arrayOfTasks.getJSONObject(0).getString(key));
                commonspec.getLogger().warn("Expected status of " + arrayOfTasks.getJSONObject(0).getString("name") + " is " + expectedStatus);
            }
            return res;
        }
        String regex_name = ".[" + name + "]*";
        HashMap<String, Long> taskTimestampMap = new HashMap<>();
        HashMap<String, String> taskStatusMap = new HashMap<>();
        for (int i = 0; i < arrayOfTasks.length(); i++) {
            JSONObject task = arrayOfTasks.getJSONObject(i);
            if (task.getString("name").matches(regex_name)) {
                if (taskTimestampMap.get(task.getString("name")) != null) {
                    if (taskTimestampMap.get(task.getString("name")) < task.getLong("timestamp")) {
                        taskTimestampMap.put(task.getString("name"), task.getLong("timestamp"));
                        taskStatusMap.put(task.getString("name"), task.getString(key));
                    }
                } else {
                    taskTimestampMap.put(task.getString("name"), task.getLong("timestamp"));
                    taskStatusMap.put(task.getString("name"), task.getString(key));
                }
            }
        }
        for (Map.Entry taskStatus : taskStatusMap.entrySet()) {
            if (!((String) taskStatus.getValue()).equalsIgnoreCase(expectedStatus)) {
                commonspec.getLogger().warn("The status of " + taskStatus.getKey() + " is " + taskStatus.getValue());
                commonspec.getLogger().warn(" Expected status of " + taskStatus.getKey() + " is " + expectedStatus);
                return false;
            }
        }
        if (taskStatusMap.size() == tasks) {
            return true;
        }
        commonspec.getLogger().error("The number of tasks deployed: " + task_counter + " are not the expected ones: " + tasks);
        return false;
    }

    /**
     * Checks in Command Center service status
     *
     * @param timeout
     * @param wait
     * @param service
     * @param numTasks
     * @param expectedStatus Expected status (healthy|unhealthy|running|stopped)
     * @throws Exception
     */
    @Given("^in less than '(\\d+)' seconds, checking each '(\\d+)' seconds, I check in CCT that the service '(.+?)'( with number of tasks '(\\d+)')? is in '(healthy|unhealthy|running|stopped)' status$")
    public void checkServiceStatus(Integer timeout, Integer wait, String service, Integer numTasks, String expectedStatus) throws Exception {
        String endPoint = "/service/deploy-api/deployments/service?instanceName=" + service;
        boolean useMarathonServices = false;
        if (ThreadProperty.get("cct-marathon-services_id") != null) {
            endPoint = "/service/cct-marathon-services/v1/services/" + service;
            useMarathonServices = true;
        }
        boolean found = false;
        boolean isDeployed = false;

        for (int i = 0; (i <= timeout); i += wait) {
            try {
                Future<Response> response = commonspec.generateRequest("GET", false, null, null, endPoint, "", null);
                commonspec.setResponse(endPoint, response.get());
                found = checkServiceStatusInResponse(expectedStatus, commonspec.getResponse().getResponse(), useMarathonServices);
                if (numTasks != null) {
                    isDeployed = checkServiceDeployed(commonspec.getResponse().getResponse(), numTasks, useMarathonServices);
                }
            } catch (Exception e) {
                commonspec.getLogger().debug("Error in request " + endPoint + " - " + e.toString());
            }
            if ((found && (numTasks == null)) || (found && (numTasks != null) && isDeployed)) {
                break;
            } else {
                if (!found) {
                    commonspec.getLogger().info(expectedStatus + " status or tasks not found after " + i + " seconds for service " + service);
                } else if (numTasks != null && !isDeployed) {
                    commonspec.getLogger().info("Tasks have not been deployed successfully after " + i + " seconds for service " + service);
                }
                if (i < timeout) {
                    Thread.sleep(wait * 1000);
                }
            }
        }
        if (!found) {
            fail(expectedStatus + " status not found after " + timeout + " seconds for service " + service);
        }
        if ((numTasks != null) && !isDeployed) {
            fail("Tasks have not been deployed successfully after " + timeout + " seconds for service " + service);
        }
    }

    /**
     * Checks in Command Center response if the service has the expected status
     *
     * @param expectedStatus Expected status (healthy|unhealthy)
     * @param response Command center response
     * @param useMarathonServices True if cct-marathon-services is used in request, False if deploy-api is used in request
     * @return If service status has the expected status
     */
    private boolean checkServiceStatusInResponse(String expectedStatus, String response, boolean useMarathonServices) {
        if (useMarathonServices) {
            JSONObject cctJsonResponse = new JSONObject(response);
            String status = cctJsonResponse.getString("status");
            String healthiness = cctJsonResponse.getString("healthiness");
            switch (expectedStatus) {
                case "healthy":
                case "unhealthy":
                    return healthiness.equalsIgnoreCase(expectedStatus);
                case "running":     return status.equalsIgnoreCase("RUNNING");
                case "stopped":     return status.equalsIgnoreCase("SUSPENDED");
                default:
            }
        } else {
            switch (expectedStatus) {
                case "healthy":     return response.contains("\"healthy\":1");
                case "unhealthy":   return response.contains("\"healthy\":2");
                case "running":     return response.contains("\"status\":2");
                case "stopped":     return response.contains("\"status\":1");
                default:
            }
        }
        return false;
    }


    /**
     * Checks in Command Center response if the service tasks are deployed successfully
     *
     * @param response Command center response
     * @param numTasks Command center response
     * @param useMarathonServices True if cct-marathon-services is used in request, False if deploy-api is used in request
     * @return If service status has the expected status
     */
    private boolean checkServiceDeployed(String response, int numTasks, boolean useMarathonServices) {

        JSONObject deployment = new JSONObject(response);
        JSONArray tasks = (JSONArray) deployment.get("tasks");
        int numTasksRunning = 0;

        for (int i = 0; i < tasks.length(); i++) {
            if (useMarathonServices) {
                numTasksRunning = tasks.getJSONObject(i).get("status").equals("RUNNING") ? (numTasksRunning + 1) : numTasksRunning;
            } else {
                numTasksRunning = tasks.getJSONObject(i).get("state").equals("TASK_RUNNING") ? (numTasksRunning + 1) : numTasksRunning;
            }
        }
        return numTasksRunning == numTasks;
    }

    /**
     * Get info from centralized configuration
     *
     * @param path
     * @param envVar
     * @param fileName
     * @throws Exception
     */
    @Given("^I get info from global config with path '(.*?)'( and save it in environment variable '(.*?)')?( and save it in file '(.*?)')?$")
    public void infoFromGlobalConfig(String path, String envVar, String fileName) throws Exception {

        String endPoint = "/service/" + ThreadProperty.get("configuration_api_id") + "/central";
        Future<Response> response;

        String pathEndpoint = "?path=" + path.replaceAll("/", "%2F");
        endPoint = endPoint.concat(pathEndpoint);

        response = commonspec.generateRequest("GET", false, null, null, endPoint, "", null, "");
        commonspec.setResponse("GET", response.get());

        if (commonspec.getResponse().getStatusCode() != 200) {
            logger.error("Request failed to endpoint: " + endPoint + " with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
            throw new Exception("Request failed to endpoint: " + endPoint + " with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
        }

        String json = commonspec.getResponse().getResponse();

        if (envVar != null) {
            ThreadProperty.set(envVar, json);
        }

        if (fileName != null) {
            writeInFile(json, fileName);
        }
    }

    /**
     * Get global configuration from centralized configuration
     *
     * @param envVar
     * @param fileName
     * @throws Exception
     */
    @Given("^I get global configuration( and save it in environment variable '(.*?)')?( and save it in file '(.*?)')?$")
    public void getGlobalConfig(String envVar, String fileName) throws Exception {

        String endPoint = "/service/" + ThreadProperty.get("configuration_api_id") + "/central/config";
        Future<Response> response;

        response = commonspec.generateRequest("GET", false, null, null, endPoint, "", null, "");
        commonspec.setResponse("GET", response.get());

        if (commonspec.getResponse().getStatusCode() != 200) {
            logger.error("Request failed to endpoint: " + endPoint + " with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
            throw new Exception("Request failed to endpoint: " + endPoint + " with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
        }

        String json = commonspec.getResponse().getResponse();

        if (envVar != null) {
            ThreadProperty.set(envVar, json);
        }

        if (fileName != null) {
            writeInFile(json, fileName);
        }
    }

    /**
     * Get schema from global configuration
     *
     * @param envVar
     * @param fileName
     * @throws Exception
     */
    @Given("^I get schema from global configuration( and save it in environment variable '(.*?)')?( and save it in file '(.*?)')?$")
    public void getSchemaGlobalConfig(String envVar, String fileName) throws Exception {

        String endPoint = "/service/" + ThreadProperty.get("configuration_api_id") + "/central/schema";
        Future<Response> response;

        response = commonspec.generateRequest("GET", false, null, null, endPoint, "", null, "");
        commonspec.setResponse("GET", response.get());

        if (commonspec.getResponse().getStatusCode() != 200) {
            logger.error("Request failed to endpoint: " + endPoint + " with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
            throw new Exception("Request failed to endpoint: " + endPoint + " with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
        }

        String json = commonspec.getResponse().getResponse();

        if (envVar != null) {
            ThreadProperty.set(envVar, json);
        }

        if (fileName != null) {
            writeInFile(json, fileName);
        }
    }

    /**
     * Get info for network Id
     *
     * @param networkId
     * @param envVar
     * @param fileName
     * @throws Exception
     */
    @Given("^I get network '(.*?)'( and save it in environment variable '(.*?)')?( and save it in file '(.*?)')?$")
    public void getNetworkById(String networkId, String envVar, String fileName) throws Exception {

        String endPoint = "/service/" + ThreadProperty.get("configuration_api_id") + "/network/" + networkId;
        Future<Response> response;

        response = commonspec.generateRequest("GET", false, null, null, endPoint, "", null, "");
        commonspec.setResponse("GET", response.get());

        if (commonspec.getResponse().getStatusCode() != 200) {
            logger.error("Request failed to endpoint: " + endPoint + " with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
            throw new Exception("Request failed to endpoint: " + endPoint + " with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
        }

        String json = commonspec.getResponse().getResponse();

        if (envVar != null) {
            ThreadProperty.set(envVar, json);
        }

        if (fileName != null) {
            writeInFile(json, fileName);
        }
    }

    /**
     * Get info for all networks
     *
     * @param envVar
     * @param fileName
     * @throws Exception
     */
    @Given("^I get all networks( and save it in environment variable '(.*?)')?( and save it in file '(.*?)')?$")
    public void getAllNetworks(String envVar, String fileName) throws Exception {

        String endPoint = "/service/" + ThreadProperty.get("configuration_api_id") + "/network/all";
        Future<Response> response;

        response = commonspec.generateRequest("GET", false, null, null, endPoint, "", null, "");
        commonspec.setResponse("GET", response.get());

        if (commonspec.getResponse().getStatusCode() != 200) {
            logger.error("Request failed to endpoint: " + endPoint + " with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
            throw new Exception("Request failed to endpoint: " + endPoint + " with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
        }

        String json = commonspec.getResponse().getResponse();

        if (envVar != null) {
            ThreadProperty.set(envVar, json);
        }

        if (fileName != null) {
            writeInFile(json, fileName);
        }
    }

    /**
     * Get Mesos configuration
     *
     * @param path
     * @param envVar
     * @param fileName
     * @throws Exception
     */
    @Given("^I get path '(.*?)' from Mesos configuration( and save it in environment variable '(.*?)')?( and save it in file '(.*?)')?$")
    public void getMesosConfiguration(String path, String envVar, String fileName) throws Exception {

        Future<Response> response;

        String endPoint = "/service/" + ThreadProperty.get("configuration_api_id") + "/mesos";
        String pathEndpoint = "?path=" + path.replaceAll("/", "%2F");
        endPoint = endPoint.concat(pathEndpoint);

        response = commonspec.generateRequest("GET", false, null, null, endPoint, "", null, "");
        commonspec.setResponse("GET", response.get());

        if (commonspec.getResponse().getStatusCode() != 200) {
            logger.error("Request failed to endpoint: " + endPoint + " with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
            throw new Exception("Request failed to endpoint: " + endPoint + " with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
        }

        String json = commonspec.getResponse().getResponse();

        if (envVar != null) {
            ThreadProperty.set(envVar, json);
        }

        if (fileName != null) {
            writeInFile(json, fileName);
        }
    }

    /**
     * Create/Update calico network
     *
     * @param timeout
     * @param wait
     * @param baseData      path to file containing the schema to be used
     * @param type          element to read from file (element should contain a json)
     * @param modifications DataTable containing the modifications to be done to the
     *                      base schema element. Syntax will be:
     *                      {@code
     *                      | <key path> | <type of modification> | <new value> |
     *                      }
     *                      where:
     *                      key path: path to the key to be modified
     *                      type of modification: DELETE|ADD|UPDATE
     *                      new value: in case of UPDATE or ADD, new value to be used
     *                      for example:
     *                      if the element read is {"key1": "value1", "key2": {"key3": "value3"}}
     *                      and we want to modify the value in "key3" with "new value3"
     *                      the modification will be:
     *                      | key2.key3 | UPDATE | "new value3" |
     *                      being the result of the modification: {"key1": "value1", "key2": {"key3": "new value3"}}
     * @throws Exception
     */
    @Given("^(in less than '(\\d+)' seconds,)?( checking each '(\\d+)' seconds, )?I (create|update) calico network '(.+?)' so that the response( does not)? contains '(.+?)' based on '([^:]+?)'( as '(json|string|gov)')? with:$")
    public void calicoNetworkTimeout(Integer timeout, Integer wait, String operation, String networkId, String contains, String responseVal, String baseData, String type, DataTable modifications) throws Exception {

        // Retrieve data
        String retrievedData = commonspec.retrieveData(baseData, type);

        // Modify data
        commonspec.getLogger().debug("Modifying data {} as {}", retrievedData, type);
        String modifiedData = commonspec.modifyData(retrievedData, type, modifications);

        String endPoint = "/service/" + ThreadProperty.get("configuration_api_id") + "/network";
        String requestType = operation.equals("create") ? "PUT" : "POST";

        Boolean searchUntilContains;
        if (contains == null || contains.isEmpty()) {
            searchUntilContains = Boolean.TRUE;
        } else {
            searchUntilContains = Boolean.FALSE;
        }
        Boolean found = !searchUntilContains;
        AssertionError ex = null;

        Future<Response> response;

        Pattern pattern = CommonG.matchesOrContains(responseVal);

        if (wait == null || timeout == null) {
            timeout = 0;
            wait = 0;
        }

        for (int i = 0; (i <= timeout); i += wait) {
            if (found && searchUntilContains) {
                break;
            }
            try {
                commonspec.getLogger().debug("Generating request {} to {} with data {} as {}", requestType, endPoint, modifiedData, type);
                response = commonspec.generateRequest(requestType, false, null, null, endPoint, modifiedData, type);
                commonspec.getLogger().debug("Saving response");
                commonspec.setResponse(requestType, response.get());
                commonspec.getLogger().debug("Checking response value");

                if (searchUntilContains) {
                    assertThat(commonspec.getResponse().getResponse()).containsPattern(pattern);
                    found = true;
                    timeout = i;
                } else {
                    assertThat(commonspec.getResponse().getResponse()).doesNotContain(responseVal);
                    found = false;
                    timeout = i;
                }
            } catch (AssertionError | Exception e) {
                if (!found) {
                    commonspec.getLogger().info("Response value not found after " + i + " seconds");
                } else {
                    commonspec.getLogger().info("Response value found after " + i + " seconds");
                }
                Thread.sleep(wait * 1000);
                if (e instanceof AssertionError) {
                    ex = (AssertionError) e;
                }
            }
            if (!found && !searchUntilContains) {
                break;
            }
        }
        if ((!found && searchUntilContains) || (found && !searchUntilContains)) {
            throw (ex);
        }
        if (searchUntilContains) {
            commonspec.getLogger().info("Success! Response value found after " + timeout + " seconds");
        } else {
            commonspec.getLogger().info("Success! Response value not found after " + timeout + " seconds");
        }
    }

    /**
     * Delete calico network
     *
     * @param timeout
     * @param wait
     * @param networkId
     * @throws Exception
     */
    @Given("^(in less than '(\\d+)' seconds,)?( checking each '(\\d+)' seconds, )?I( force to)? delete calico network '(.+?)' so that the response( does not)? contains '(.+?)'$")
    public void deleteCalicoNetworkTimeout(Integer timeout, Integer wait, String force, String networkId, String contains, String responseVal) throws Exception {

        if (force == null && (networkId.equals("logs") || networkId.equals("stratio") || networkId.equals("metrics") || networkId.equals("stratio-shared"))) {
            throw new Exception("It is not possible deleting networks stratio, metrics, logs or stratio-shared");
        }
        String endPoint = "/service/" + ThreadProperty.get("configuration_api_id") + "/network/" + networkId;
        String requestType = "DELETE";

        if (wait == null || timeout == null) {
            timeout = 0;
            wait = 0;
        }

        Boolean searchUntilContains;
        if (contains == null || contains.isEmpty()) {
            searchUntilContains = Boolean.TRUE;
        } else {
            searchUntilContains = Boolean.FALSE;
        }
        Boolean found = !searchUntilContains;
        AssertionError ex = null;

        Future<Response> response;

        Pattern pattern = CommonG.matchesOrContains(responseVal);

        for (int i = 0; (i <= timeout); i += wait) {
            if (found && searchUntilContains) {
                break;
            }
            try {
                commonspec.getLogger().debug("Generating request {} to {} with data {} as {}", requestType, endPoint, null, null);
                response = commonspec.generateRequest(requestType, false, null, null, endPoint, null, null);
                commonspec.getLogger().debug("Saving response");
                commonspec.setResponse(requestType, response.get());
                commonspec.getLogger().debug("Checking response value");

                if (searchUntilContains) {
                    assertThat(commonspec.getResponse().getResponse()).containsPattern(pattern);
                    found = true;
                    timeout = i;
                } else {
                    assertThat(commonspec.getResponse().getResponse()).doesNotContain(responseVal);
                    found = false;
                    timeout = i;
                }
            } catch (AssertionError | Exception e) {
                if (!found) {
                    commonspec.getLogger().info("Response value not found after " + i + " seconds");
                } else {
                    commonspec.getLogger().info("Response value found after " + i + " seconds");
                }
                Thread.sleep(wait * 1000);
                if (e instanceof AssertionError) {
                    ex = (AssertionError) e;
                }
            }
            if (!found && !searchUntilContains) {
                break;
            }
        }
        if ((!found && searchUntilContains) || (found && !searchUntilContains)) {
            throw (ex);
        }
        if (searchUntilContains) {
            commonspec.getLogger().info("Success! Response value found after " + timeout + " seconds");
        } else {
            commonspec.getLogger().info("Success! Response value not found after " + timeout + " seconds");
        }
    }

    /**
     * Get service schema
     *
     * @param level     schema level
     * @param service   service name
     * @param model     service model
     * @param version   service version
     * @param envVar    environment variable to save response in
     * @param fileName  file name where response is saved
     * @throws Exception
     */
    @Given("^I get schema( with level '(\\d+)')? from service '(.+?)' with model '(.+?)' and version '(.+?)'( and save it in environment variable '(.*?)')?( and save it in file '(.*?)')?$")
    public void getServiceSchema(Integer level, String service, String model, String version, String envVar, String fileName) throws Exception {

        if (level == null) {
            level = 1;
        }

        String endPoint = "/service/" + ThreadProperty.get("deploy_api_id") + "/deploy/" + service + "/" + model + "/" + version + "/schema?enriched=true&level=" + level;
        Future<Response> response = commonspec.generateRequest("GET", false, null, null, endPoint, "", null, "");
        commonspec.setResponse("GET", response.get());

        if (commonspec.getResponse().getStatusCode() != 200) {
            logger.error("Request failed to endpoint: " + endPoint + " with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
            throw new Exception("Request to endpoint: " + endPoint + " failed with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
        }

        String json = commonspec.getResponse().getResponse();

        if (envVar != null || fileName != null) {
            DcosSpec dcosSpec = new DcosSpec(commonspec);
            dcosSpec.convertJSONSchemaToJSON(json, envVar, fileName);
        }

    }

    private void writeInFile(String json, String fileName) throws Exception {

        // Create file (temporary) and set path to be accessible within test
        File tempDirectory = new File(System.getProperty("user.dir") + "/target/test-classes/");
        String absolutePathFile = tempDirectory.getAbsolutePath() + "/" + fileName;
        commonspec.getLogger().debug("Creating file {} in 'target/test-classes'", absolutePathFile);
        // Note that this Writer will delete the file if it exists
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(absolutePathFile), StandardCharsets.UTF_8));
        try {
            out.write(json);
        } catch (Exception e) {
            commonspec.getLogger().error("Custom file {} hasn't been created:\n{}", absolutePathFile, e.toString());
        } finally {
            out.close();
        }

        Assertions.assertThat(new File(absolutePathFile).isFile());
    }

    /**
     * Install service
     * @param service   service name
     * @param folder    folder where service are going to be installed
     * @param model     service model
     * @param version   service version
     * @param name      service instance name
     * @param tenant    tenant where to install service in
     * @param jsonFile  marathon json to deploy
     * @throws Exception
     */
    @Given("^I install service '(.+?)'( in folder '(.+?)')? with model '(.+?)' and version '(.+?)' and instance name '(.+?)' in tenant '(.+?)' using json '(.+?)'$")
    public void installServiceFromMarathonJson(String service, String folder, String model, String version, String name, String tenant, String jsonFile) throws Exception {
        String endPoint = "/service/" + ThreadProperty.get("deploy_api_id") + "/deploy/" + service + "/" + model + "/" + version + "/schema?tenantId=" + tenant;
        String data = this.commonspec.retrieveData(jsonFile, "json");

        Future<Response> response = commonspec.generateRequest("POST", true, null, null, endPoint, data, "json");
        commonspec.setResponse("POST", response.get());

        if (commonspec.getResponse().getStatusCode() != 202) {
            logger.error("Request to endpoint: " + endPoint + " failed with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
            throw new Exception("Request to endpoint: " + endPoint + " failed with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
        }

        // Check Application in API
        RestSpec restSpec = new RestSpec(commonspec);

        String endPointStatus;
        if (ThreadProperty.get("cct-marathon-services_id") == null) {
            endPointStatus = "/service/" + ThreadProperty.get("deploy_api_id") + "/deploy/status/all";
        } else {
            endPointStatus = "/service/" + ThreadProperty.get("cct-marathon-services_id") + "/v1/services?tenant=" + tenant;
        }

        if (folder != null && folder.startsWith("/")) {
            folder = folder.substring(1);
        }
        if (folder != null && folder.endsWith("/")) {
            folder = folder.substring(folder.length() - 1);
        }

        String serviceName = "/" + name;
        if (folder != null) {
            serviceName = "/" + folder + "/" + name;
        }
        if (!"NONE".equals(tenant)) {
            serviceName = "/" + tenant + "/" + tenant + "-" + name;
            if (folder != null) {
                serviceName =  "/" + tenant + "/" + folder + "/" + tenant + "-" + name;
            }
        }

        restSpec.sendRequestTimeout(200, 20, "GET", endPointStatus, null, serviceName);
    }

    /**
     * Uninstall service from tenant
     *
     * @param service   service name
     * @param tenant    tenant where service is installed
     * @throws Exception
     */
    @Given("^I uninstall service '(.+?)'( in folder '(.+?)')? from tenant '(.+?)'$")
    public void uninstallService(String service, String folder, String tenant) throws Exception {
        if (folder != null && folder.startsWith("/")) {
            folder = folder.substring(1);
        }
        if (folder != null && folder.endsWith("/")) {
            folder = folder.substring(folder.length() - 1);
        }

        String serviceName = service;
        if (folder != null) {
            serviceName = folder + "/" + service;
        }
        if (!"NONE".equals(tenant)) {
            serviceName = tenant + "/" + tenant + "-" + service;
            if (folder != null) {
                serviceName =  tenant + "/" + folder + "/" + tenant + "-" + service;
            }
        }

        String endPoint = "/service/" + ThreadProperty.get("deploy_api_id") + "/deploy/uninstall?app=" + serviceName;
        Future<Response> response = commonspec.generateRequest("DELETE", true, null, null, endPoint, "", "json");
        commonspec.setResponse("DELETE", response.get());

        if (commonspec.getResponse().getStatusCode() != 202 && commonspec.getResponse().getStatusCode() != 200) {
            logger.error("Request to endpoint: " + endPoint + " failed with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
            throw new Exception("Request to endpoint: " + endPoint + " failed with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
        }

        // Check service has disappeared
        RestSpec restSpec = new RestSpec(commonspec);

        String endPointStatus;
        String key;
        if (ThreadProperty.get("cct-marathon-services_id") == null) {
            endPointStatus = "/service/" + ThreadProperty.get("deploy_api_id") + "/deploy/status/all";
            key = "\"serviceName\"";
        } else {
            endPointStatus = "/service/" + ThreadProperty.get("cct-marathon-services_id") + "/v1/services?tenant=" + tenant;
            key = "\"id\"";
        }

        restSpec.sendRequestTimeout(200, 20, "GET", endPointStatus, "does not", key + ":" + "\"" + serviceName + "\"");
        // Check all resources have been freed
        DcosSpec dcosSpec = new DcosSpec(commonspec);
        dcosSpec.checkResources(serviceName);
    }

    /**
     * Upload rules
     *
     * @param rulesPath   path to rules zip file
     * @param priority    (optional) priority to assign to the rules
     * @param version     (optional) version to use for rules
     * @throws Exception
     */
    @Given("^I upload rules file '(.+?)'( with priority '(.+?)')?( overriding version to '(.+?)')?")
    public void uploadRules(String rulesPath, String priority, String version) throws Exception {
        // Check file exists
        File rules = new File(rulesPath);
        Assertions.assertThat(rules.exists()).as("File: " + rulesPath + " does not exist.").isTrue();

        // Obtain endpoint
        if (ThreadProperty.get("deploy_api_id") == null) {
            fail("deploy_api_id variable is not set. Check deploy-api is installed and @dcos annotation is working properly.");
        }
        String endPointUpload = "/service/" + ThreadProperty.get("deploy_api_id") + "/knowledge/upload";

        // Obtain URL
        String restURL = "https://" + commonspec.getRestHost() + commonspec.getRestPort() + endPointUpload;

        // Form query parameters
        String headers = "-H \"accept: */*\" -H \"Content-Type: multipart/form-data\"";
        String forms = "-F \"file=@" + rulesPath + ";type=application/zip\"";

        if (priority == null) {
            priority = "0";
        }
        forms = forms + " -F \"priority=" + priority  + "\"";

        if (version != null) {
            forms = forms + " -F \"version=" + version + "\"";
        }

        String cookie = "-H \"Cookie:dcos-acs-auth-cookie=" + ThreadProperty.get("dcosAuthCookie") + "\"";
        String command = "curl -X POST -k " + cookie + " \"" + restURL + "\" " + headers + " " + forms;

        // Execute command
        commonspec.runLocalCommand(command);

        Assertions.assertThat(commonspec.getCommandExitStatus()).isEqualTo(0);
        Assertions.assertThat(commonspec.getCommandResult()).as("Not possible to upload rules: " + commonspec.getCommandResult()).doesNotContain("Error");
    }

    /**
     * Upload descriptors
     *
     * @param descriptorsPath   path to descriptors zip file
     * @param version     (optional) version to use for rules
     * @throws Exception
     */
    @Given("^I upload descriptors file '(.+?)'( overriding version to '(.+?)')?")
    public void uploadDescriptors(String descriptorsPath, String version) throws Exception {
        String headers = "";
        String forms = "";
        String op = "";

        // Check file exists
        File descriptors = new File(descriptorsPath);
        Assertions.assertThat(descriptors.exists()).as("File: " + descriptorsPath + " does not exist.").isTrue();

        // Obtain endpoint
        if (ThreadProperty.get("deploy_api_id") == null && ThreadProperty.get("cct-universe_id") == null) {
            fail("deploy_api_id variable and cct-universe_id are not set. Check deploy-api or cct-universe are installed and @dcos annotation is working properly.");
        }

        // Obtain cookie
        String cookie = "-H \"Cookie:dcos-acs-auth-cookie=" + ThreadProperty.get("dcosAuthCookie") + "\"";

        String endPointUpload = "";
        if (ThreadProperty.get("cct-universe_id") != null) {
            endPointUpload = "/service/" + ThreadProperty.get("cct-universe_id") + "/v1/descriptors";
            headers = "-H \"accept: application/json\" -H \"Content-Type: multipart/form-data\"";
            forms = "-F \"file=@" + descriptorsPath + ";type=application/zip\"";
            op = "PUT";
        } else {
            endPointUpload = "/service/" + ThreadProperty.get("deploy_api_id") + "/universe/upload";
            headers = "-H \"Content-Type: multipart/form-data\"";
            forms = "-F \"file=@" + descriptorsPath + "\"";

            if (version != null) {
                forms = forms + " -F \"version=" + version + "\"";
            }
            op = "POST";
        }

        // Obtain URL
        String restURL = "https://" + commonspec.getRestHost() + commonspec.getRestPort() + endPointUpload;

        // Form query
        String command = "curl -X " + op + " -k " + cookie + " \"" + restURL + "\" " + headers + " " + forms;

        // Execute command
        commonspec.runLocalCommand(command);

        String result = commonspec.getCommandResult();

        Assertions.assertThat(commonspec.getCommandExitStatus()).isEqualTo(0);

        if (ThreadProperty.get("cct-universe_id") != null) {
            String accepted = commonspec.getJSONPathString(result, "$.accepted", null);
            String rejected = commonspec.getJSONPathString(result, "$.rejected", null);

            if ("[]".equals(accepted)) {
                fail("No descriptors have been uploaded correctly: " + result);
            }

            if (!"[]".equals(rejected)) {
                fail("There are descriptors that have been rejected: " + rejected);
            }
        } else {
            String added = commonspec.getJSONPathString(result, "$.added", null);
            String error = commonspec.getJSONPathString(result, "$.error", null);
            String existing = commonspec.getJSONPathString(result, "$.existing", null);

            if ("[]".equals(added)) {
                fail("No descriptors have been uploaded correctly: " + result);
            }

            if (!"[]".equals(error)) {
                fail("There are descriptors that have been rejected: " + result);
            }

            if (!"[]".equals(existing)) {
                fail("There are descriptors that already exist: " + result);
            }
        }
    }

    /**
     * Update a deployed service
     *
     * @param serviceName       name of the service to be updated
     * @param folder            (optional) name of the folder where service is deployed
     * @param tenant            tenant where service is deployed
     * @param version           version of the deployed service
     * @param modifications     modifications to perform in the deployed json
     * @throws Exception
     */
    @Given("^I update service '(.+?)'( in folder '(.+?)')? in tenant '(.+?)'( based on version '(.+?)')?( based on json '(.+?)')? with:$")
    public void updateCCTService(String serviceName, String folder, String tenant, String version, String jsonFile, DataTable modifications) throws Exception {
        if (ThreadProperty.get("deploy_api_id") == null) {
            fail("deploy_api_id variable is not set. Check deploy-api is installed and @dcos annotation is working properly.");
        }

        // obtain service name
        if (folder != null && folder.startsWith("/")) {
            folder = folder.substring(1);
        }
        if (folder != null && folder.endsWith("/")) {
            folder = folder.substring(folder.length() - 1);
        }

        String service = serviceName;
        if (folder != null) {
            service = folder + "/" + serviceName;
        }
        if (!"NONE".equals(tenant)) {
            service = tenant + "/" + tenant + "-" + serviceName;
            if (folder != null) {
                service =  tenant + "/" + folder + "/" + tenant + "-" + serviceName;
            }
        }

        // Check service is running
        try {
            checkServiceStatus(10, 1, service, null, "running");
        } catch (Exception e) {
            logger.error("Service: " + service + " is not deployed in cluster.");
            throw e;
        }

        String deployedJson = "";

        if (version == null && jsonFile == null || version != null && jsonFile != null) {
            throw new Exception("Version or json file must be defined (but NOT both)");
        }

        if (version != null) {
            // Obtain deployed service json
            String endpointJson = "/service/" + ThreadProperty.get("deploy_api_id") + "/update/" + service + "?version=" + version;
            Future<Response> responseJson = commonspec.generateRequest("GET", true, null, null, endpointJson, "", "json");
            commonspec.setResponse("GET", responseJson.get());

            if (commonspec.getResponse().getStatusCode() != 200) {
                logger.error("Request to endpoint: " + endpointJson + " failed with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
                throw new Exception("Request to endpoint: " + endpointJson + " failed with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
            }

            // Modify json according to provided changes
            deployedJson = commonspec.getResponse().getResponse();

        }

        if (jsonFile != null) {
            deployedJson = this.commonspec.retrieveData(jsonFile, "json");
        }

        String modifiedData;
        if (modifications != null) {
            modifiedData = commonspec.modifyData(deployedJson, "json", modifications);
        } else {
            modifiedData = deployedJson;
        }

        // Deploy new json
        String endpointUpdate = "/service/" + ThreadProperty.get("deploy_api_id") + "/update/" + service;
        Future<Response> responseUpdate = commonspec.generateRequest("PUT", true, null, null, endpointUpdate, modifiedData, "json");
        commonspec.setResponse("PUT", responseUpdate.get());

        if (commonspec.getResponse().getStatusCode() != 202) {
            logger.error("Request to endpoint: " + endpointUpdate + " failed with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
            throw new Exception("Request to endpoint: " + endpointUpdate + " failed with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
        }
    }

    /**
     * Update a deployed service
     *
     * @param serviceName       name of the service to be updated
     * @param folder            (optional) name of the folder where service is deployed
     * @param tenant            tenant where service is deployed
     * @param version           version of the deployed service
     * @throws Exception
     */
    @Given("^I update service '(.+?)'( in folder '(.+?)')? in tenant '(.+?)'( based on version '(.+?)')?( based on json '(.+?)')?$")
    public void updateCCTService(String serviceName, String folder, String tenant, String version, String jsonFile) throws Exception {
        updateCCTService(serviceName, folder, tenant, version, jsonFile, null);
    }


    /**
     * Upload a descriptor
     *
     * @param service           name of the descriptor to be updated
     * @param model             model of the descriptor
     * @param version           version of the descriptor
     * @param originalJson      base descriptor json
     * @param modifications     modifications to perform in the descriptor json
     * @throws Exception
     */
    @Given("^I upload descriptor for service '(.+?)', model '(.+?)' version '(.+?)' based on '(.+?)' with:$")
    public void uploadCCTDescriptor(String service, String model, String version, String originalJson, DataTable modifications) throws Exception {
        // Obtain endpoint
        if (ThreadProperty.get("deploy_api_id") == null && ThreadProperty.get("cct-universe_id") == null) {
            fail("deploy_api_id variable and cct-universe_id are not set. Check deploy-api or cct-universe are installed and @dcos annotation is working properly.");
        }

        String endpoint;
        String op;
        if (ThreadProperty.get("cct-universe_id") != null) {
            endpoint = "/service/" + ThreadProperty.get("cct-universe_id") + "/v1/descriptors/" + service + "/" + model + "/" + version;
            op = "PUT";
        } else {
            endpoint = "/service/" + ThreadProperty.get("deploy_api_id") + "/universe/" + service + "/" + model + "/" + version + "/descriptor";
            op = "POST";
        }

        // Retrieve data
        String retrievedData = commonspec.retrieveData(originalJson, "json");
        // Modify json
        String modifiedData = commonspec.modifyData(retrievedData, "json", modifications);

        // Upload new descriptor
        Future<Response> responseUpdate = commonspec.generateRequest(op, true, null, null, endpoint, modifiedData, "json");
        commonspec.setResponse(op, responseUpdate.get());

        if (commonspec.getResponse().getStatusCode() != 200 && commonspec.getResponse().getStatusCode() != 201) {
            logger.error("Upload descriptor: " + endpoint + " failed with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
            throw new Exception("Upload descriptor: " + endpoint + " failed with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
        }
    }

    /**
     * Update a descriptor
     *
     * @param service           name of the descriptor to be updated
     * @param model             model of the descriptor
     * @param version           version of the descriptor
     * @param originalJson      base descriptor json
     * @param modifications     modifications to perform in the descriptor json
     * @throws Exception
     */
    @Given("^I update descriptor for service '(.+?)', model '(.+?)' version '(.+?)' based on '(.+?)' with:$")
    public void updateCCTDescriptor(String service, String model, String version, String originalJson, DataTable modifications) throws Exception {
        // Obtain endpoint
        if (ThreadProperty.get("deploy_api_id") == null && ThreadProperty.get("cct-universe_id") == null) {
            fail("deploy_api_id variable and cct-universe_id are not set. Check deploy-api or cct-universe are installed and @dcos annotation is working properly.");
        }

        String endpoint;
        if (ThreadProperty.get("cct-universe_id") != null) {
            endpoint = "/service/" + ThreadProperty.get("cct-universe_id") + "/v1/descriptors/" + service + "/" + model + "/" + version;
        } else {
            endpoint = "/service/" + ThreadProperty.get("deploy_api_id") + "/universe/" + service + "/" + model + "/" + version + "/descriptor";
        }

        // Retrieve data
        String retrievedData = commonspec.retrieveData(originalJson, "json");
        // Modify json
        String modifiedData = commonspec.modifyData(retrievedData, "json", modifications);

        // Update descriptor
        Future<Response> responseUpdate = commonspec.generateRequest("PUT", true, null, null, endpoint, modifiedData, "json");
        commonspec.setResponse("PUT", responseUpdate.get());

        if (commonspec.getResponse().getStatusCode() != 200 && commonspec.getResponse().getStatusCode() != 201) {
            logger.error("Update descriptor: " + endpoint + " failed with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
            throw new Exception("Update descriptor: " + endpoint + " failed with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
        }
    }

    /**
     * Delete a descriptor
     *
     * @param service           name of the descriptor to be updated
     * @param model             model of the descriptor
     * @param version           version of the descriptor
     * @throws Exception
     */
    @Given("^I delete descriptor for service '(.+?)', model '(.+?)' version '(.+?)'$")
    public void deleteCCTDescriptor(String service, String model, String version) throws Exception {
        // Obtain endpoint
        if (ThreadProperty.get("deploy_api_id") == null && ThreadProperty.get("cct-universe_id") == null) {
            fail("deploy_api_id variable and cct-universe_id are not set. Check deploy-api or cct-universe are installed and @dcos annotation is working properly.");
        }

        String endpoint;
        if (ThreadProperty.get("cct-universe_id") != null) {
            endpoint = "/service/" + ThreadProperty.get("cct-universe_id") + "/v1/descriptors/" + service + "/" + model + "/" + version;
        } else {
            endpoint = "/service/" + ThreadProperty.get("deploy_api_id") + "/universe/" + service + "/" + model + "/" + version + "/descriptor";
        }

        // Delete descriptor
        Future<Response> responseUpdate = commonspec.generateRequest("DELETE", true, null, null, endpoint, "", "json");
        commonspec.setResponse("DELETE", responseUpdate.get());

        if (commonspec.getResponse().getStatusCode() != 200 && commonspec.getResponse().getStatusCode() != 201 && commonspec.getResponse().getStatusCode() != 204) {
            logger.error("Delete descriptor: " + endpoint + " failed with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
            throw new Exception("Delete descriptor: " + endpoint + " failed with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
        }
    }

    /**
     * Read value from centralized configuration path
     *
     * @param path      path to read value from (separated with '/')
     * @param envVar    environment variable where to store the read value
     * @throws Exception
     */
    @When("^I read value in path '(.+?)' from central configuration and save it in environment variable '(.+?)'$")
    public void readValueCentralConfig(String path, String envVar) throws Exception {
        if (ThreadProperty.get("configuration_api_id") == null) {
            fail("configuration_api_id variable is not set. Check configuratio-api is installed and @dcos annotation is working properly.");
        }

        // Set sso token
        DcosSpec dcosSpec = new DcosSpec(commonspec);
        dcosSpec.setGoSecSSOCookie(null, null, ThreadProperty.get("EOS_ACCESS_POINT"), ThreadProperty.get("DCOS_USER"), System.getProperty("DCOS_PASSWORD"), ThreadProperty.get("DCOS_TENANT"), null);
        // Securely send requests
        commonspec.setRestProtocol("https://");
        commonspec.setRestHost(ThreadProperty.get("EOS_ACCESS_POINT"));
        commonspec.setRestPort(":443");

        String fullPath = "/dcs/v1/fabric" + path;
        String endpoint = "/service/" + ThreadProperty.get("configuration_api_id") + "/etcd?path=" + fullPath;

        Future<Response> responseETCD = commonspec.generateRequest("GET", false, null, null, endpoint, "", null);
        commonspec.setResponse("GET", responseETCD.get());

        if (commonspec.getResponse().getStatusCode() != 200) {
            logger.error("Obtain info from ETCD: " + endpoint + " failed with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
            throw new Exception("Obtain info from ETCD: " + endpoint + " failed with status code: " + commonspec.getResponse().getStatusCode() + " and response: " + commonspec.getResponse().getResponse());
        }

        ThreadProperty.set(envVar, commonspec.getResponse().getResponse());
    }

    @When("^I( force)? create '(certificate|keytab|password|password_nouser)' '(.+?)' using deploy-api (with|without) parameters( path '(.+?)')?( cn '(.+?)')?( name '(.+?)')?( alt '(.+?)')?( organization '(.+?)')?( principal '(.+?)')?( realm '(.+?)')?( user '(.+?)')?( password '(.+?)')?$")
    public void createSecret(String force, String secretType, String secret, String withOrWithout, String path, String cn, String name, String alt, String organizationName, String principal, String realm, String user, String password) throws Exception {
        String baseUrl = "/service/" + ThreadProperty.get("deploy_api_id") + "/secrets";
        String secretTypeAux;
        String urlParams;
        switch (secretType) {
            case "certificate":
                urlParams = getCertificateUrlParams(secret, path, cn, name, alt, organizationName);
                secretTypeAux = "certificates";
                break;
            case "keytab":
                urlParams = getKeytabUrlParams(secret, path, name, principal, realm);
                secretTypeAux = "kerberos";
                break;
            case "password":
                urlParams = getPasswordUrlParams(secret, path, name, user, password);
                secretTypeAux = "passwords";
                break;
            default:
                urlParams = "";
                secretTypeAux = "default";
        }
        if (force != null) {
            String pathAux = path != null ? path.replaceAll("/", "%2F") + secret : "%2Fuserland%2F" + secretTypeAux + "%2F" + secret;
            restSpec.sendRequestNoDataTable("DELETE", baseUrl + "?path=" + pathAux, null, null, null);
        }
        if (!secretType.equals("password_nouser")) {
            restSpec.sendRequestNoDataTable("POST", baseUrl + "/" + secretType + urlParams, null, null, null);
        } else {
            String pathAux = (path != null ? path.replaceAll("/", "%2F") + secret : "%2Fuserland%2Fpasswords%2F" + secret) + "%2F" + (name != null ? name : secret);
            String filePath = createCustomSecretFile(password != null ? password : secret);
            restSpec.sendRequestNoDataTable("POST", baseUrl + "/custom?path=" + pathAux, null, filePath, "json");
        }
    }

    private String createCustomSecretFile(String password) throws IOException {
        File tempDirectory = new File(System.getProperty("user.dir") + "/target/test-classes/");
        String fileName = System.currentTimeMillis() + ".json";
        String absolutePathFile = tempDirectory.getAbsolutePath() + "/" + fileName;
        commonspec.getLogger().debug("Creating file {} in 'target/test-classes'", absolutePathFile);
        // Note that this Writer will delete the file if it exists
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(absolutePathFile), StandardCharsets.UTF_8));
        try {
            out.write("{\"pass\": \"" + password + "\"}");
        } catch (Exception e) {
            commonspec.getLogger().error("Custom file {} hasn't been created:\n{}", absolutePathFile, e.toString());
        } finally {
            out.close();
        }
        return fileName;
    }

    @When("^I delete '(certificate|keytab|password)' '(.+?)'( located in path '(.+?)')?$")
    public void removeSecret(String secretType, String secret, String path) throws Exception {
        String baseUrl = "/service/" + ThreadProperty.get("deploy_api_id") + "/secrets";
        String secretTypeAux;
        switch (secretType) {
            case "certificate":
                secretTypeAux = "certificates";
                break;
            case "keytab":
                secretTypeAux = "kerberos";
                break;
            case "password":
                secretTypeAux = "passwords";
                break;
            default:
                secretTypeAux = "default";
        }
        String pathAux = path != null ? path.replaceAll("/", "%2F") + secret : "%2Fuserland%2F" + secretTypeAux + "%2F" + secret;
        restSpec.sendRequestNoDataTable("DELETE", baseUrl + "?path=" + pathAux, null, null, null);
        restSpec.sendRequestNoDataTable("GET", baseUrl + "?path=" + pathAux, null, null, null);
        restSpec.assertResponseStatusLength(404, null, null);
    }

    private String getCertificateUrlParams(String secret, String path, String cn, String name, String alt, String organizationName) {
        String pathAux = path != null ? path.replaceAll("/", "%2F") + secret : "%2Fuserland%2Fcertificates%2F" + secret;
        String cnAux = cn != null ? cn : secret;
        String nameAux = name != null ? name : secret;
        String urlParams = "?path=" + pathAux + "&cn=" + cnAux + "&name=" + nameAux;
        if (alt != null) {
            urlParams = urlParams + "&alt=" + alt;
        }
        if (organizationName != null) {
            urlParams = urlParams + "&organizationName=" + organizationName;
        }
        return urlParams;
    }

    private String getKeytabUrlParams(String secret, String path, String name, String principal, String realm) throws Exception {
        String pathAux = path != null ? path.replaceAll("/", "%2F") + secret : "%2Fuserland%2Fkerberos%2F" + secret;
        String principalAux = principal != null ? principal : secret;
        String nameAux = name != null ? name : secret;
        String realmAux = realm != null ? realm : ThreadProperty.get("EOS_REALM");
        if (realmAux == null) {
            throw new Exception("Realm is mandatory to generate keytab");
        }
        return "?path=" + pathAux + "&principal=" + principalAux + "&name=" + nameAux + "&realm=" + realmAux;
    }

    private String getPasswordUrlParams(String secret, String path, String name, String user, String password) {
        String pathAux = path != null ? path.replaceAll("/", "%2F") + secret : "%2Fuserland%2Fpasswords%2F" + secret;
        String nameAux = name != null ? name : secret;
        String userAux = user != null ? user : secret;
        String passwordAux = password != null ? password : secret;
        return "?path=" + pathAux + "&name=" + nameAux + "&password=" + passwordAux + "&user=" + userAux;
    }

    @When("^I login to '(.+?)' based on '([^:]+?)' as '(json|string)'$")
    public void loginUser(String endPoint, String baseData, String type) throws Exception {
        restSpec.sendRequestNoDataTable("POST", endPoint, null, baseData, type);
    }

    @When("^I login to '(.+?)' based on '([^:]+?)' as '(json|string)' with:$")
    public void loginUser(String endPoint, String baseData, String type, DataTable modifications) throws Exception {
        restSpec.sendRequest("POST", endPoint, null, baseData, type, modifications);
    }

    @When("^I logout from '(.+?)'$")
    public void logoutUser(String endPoint) throws Exception {
        restSpec.sendRequestNoDataTable("GET", endPoint, null, "", "");
    }

}
