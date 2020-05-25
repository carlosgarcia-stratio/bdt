package com.stratio.qa.clients.cct;

import com.ning.http.client.Response;
import com.stratio.qa.clients.BaseClient;
import com.stratio.qa.models.cct.marathonServiceApi.DeployedService;
import com.stratio.qa.models.cct.marathonServiceApi.DeployedServicesResponse;
import com.stratio.qa.models.cct.marathonServiceApi.TaskLogsResponse;
import com.stratio.qa.specs.CommonG;
import com.stratio.qa.utils.ThreadProperty;

import java.util.HashMap;
import java.util.Map;

public class CctMarathonServiceApiClient extends BaseClient {

    private static CctMarathonServiceApiClient CLIENT;

    public static CctMarathonServiceApiClient getInstance(CommonG common) {
        if (CLIENT == null) {
            CLIENT = new CctMarathonServiceApiClient(common);
        }
        return CLIENT;
    }

    private CctMarathonServiceApiClient(CommonG common) {
        super(common);
    }

    public DeployedService getService(String serviceId) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/cct-marathon-services/v1/services");
        url = url.concat(serviceId);

        Response response = get(url);
        return map(response, DeployedService.class);
    }

    public DeployedService getService(String serviceId, int tpage, int tsize) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/cct-marathon-services/v1/services");
        url = url.concat(serviceId);

        Map<String, String> queryParams = new HashMap<String, String>() { {
                put("tpage", Integer.toString(tpage));
                put("tsize", Integer.toString(tsize));
            } };

        Response response = get(url, queryParams);
        return map(response, DeployedService.class);
    }

    public TaskLogsResponse getLogPaths(String taskId) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/cct-marathon-services/v1/services/tasks/" + taskId + "/logs");

        Response response = get(url);
        return map(response, TaskLogsResponse.class);
    }

    public DeployedServicesResponse getDeployedServices(String tenant) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/cct-marathon-services/v1/services?tenant=");
        url = url.concat(tenant);

        Response response = get(url);
        return map(response, DeployedServicesResponse.class);
    }

}
