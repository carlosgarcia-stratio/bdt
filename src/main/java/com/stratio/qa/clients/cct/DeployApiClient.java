package com.stratio.qa.clients.cct;

import com.ning.http.client.Response;
import com.stratio.qa.clients.BaseClient;
import com.stratio.qa.models.BaseResponse;
import com.stratio.qa.models.BaseResponseList;
import com.stratio.qa.models.cct.deployApi.DeployedApp;
import com.stratio.qa.models.cct.deployApi.SandboxItem;
import com.stratio.qa.models.cct.deployApi.ServiceStatusModel;
import com.stratio.qa.models.cct.deployApi.TearDownResponse;
import com.stratio.qa.specs.CommonG;
import com.stratio.qa.utils.ThreadProperty;

public class DeployApiClient extends BaseClient {

    private static DeployApiClient CLIENT;

    public static DeployApiClient getInstance(CommonG common) {
        if (CLIENT == null) {
            CLIENT = new DeployApiClient(common);
        }
        return CLIENT;
    }

    private DeployApiClient(CommonG common) {
        super(common);
    }

    public DeployedApp getDeployedApp(String appId) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/deploy-api/deployments/services?instanceName=");
        String endpoint = url.concat(appId);

        Response response = get(endpoint);
        return map(response, DeployedApp.class);
    }

    public BaseResponseList<SandboxItem> getLogPaths(String taskId) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/deploy-api/deployments/logs/");
        String endpoint = url.concat(taskId);

        Response response = get(endpoint);
        return mapList(response, SandboxItem.class);
    }

    public TearDownResponse tearDown(String serviceId) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/deploy-api/deploy/teardown?frameworkName=");
        String endpoint =  url.concat(serviceId);

        Response response = delete(endpoint);
        return map(response, TearDownResponse.class);
    }

    public BaseResponseList<ServiceStatusModel> getDeployedServices() throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/deploy-api/deploy/status/all");

        Response response = delete(url);
        return mapList(response, ServiceStatusModel.class);
    }

    public BaseResponse scale(int instances, String service) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/deploy-api/deploy/scale?");
        url = url.concat("instances=").concat(Integer.toString(instances)).concat("&serviceName=").concat(service);

        Response response = put(url);
        return map(response);
    }


}
