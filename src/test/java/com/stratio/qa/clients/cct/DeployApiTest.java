package com.stratio.qa.clients.cct;

import com.stratio.qa.clients.BaseClientTest;
import com.stratio.qa.models.BaseResponseList;
import com.stratio.qa.models.cct.deployApi.DeployedApp;
import com.stratio.qa.utils.ThreadProperty;
import org.mockserver.client.MockServerClient;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;


public class DeployApiTest extends BaseClientTest {

    private DeployApiClient deployApiClient;

    protected DeployApiClient getClient() {
        setHTTPClient();
        return DeployApiClient.getInstance(commong);
    }

    @BeforeClass
    public void start() throws Exception {
        startMockServer();
        deployApiClient = getClient();
        deployApiClient.setPort(Integer.toString(port));
    }

    private void setEnvs(){
        ThreadProperty.set("EOS_ACCESS_POINT", "localhost");
        ThreadProperty.set("deploy_api_id", "deploy-api");
    }

    @Test
    public void example() throws Exception {
        setEnvs();
        String endpoint = "/service/";
        endpoint = endpoint.concat(ThreadProperty.get("deploy_api_id")).concat("/deployments");
        new MockServerClient("localhost", port)
            .when(
                request()
                    .withMethod("GET")
                    .withPath(endpoint)
            )
            .respond(
                response()
                    .withStatusCode(200)
                    .withBody(DeployApiTestConstants.getAppsResponseOK)
            );

        BaseResponseList<DeployedApp> responseList = deployApiClient.getDeployedApps();
        assertThat(responseList.getList()).as("List should not be empty").isNotEmpty();
        assertThat(responseList.getList().size()).as("Response elements do not match").isEqualTo(16);
    }

    @AfterClass
    public void stop() {
        stopMockServer();
    }

}
