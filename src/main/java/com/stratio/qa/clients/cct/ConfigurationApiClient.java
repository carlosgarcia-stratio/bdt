package com.stratio.qa.clients.cct;

import com.ning.http.client.Response;
import com.stratio.qa.clients.BaseClient;
import com.stratio.qa.models.BaseResponse;
import com.stratio.qa.models.BaseResponseList;
import com.stratio.qa.models.cct.configurationApi.CalicoConfiguration;
import com.stratio.qa.specs.CommonG;
import com.stratio.qa.utils.ThreadProperty;

public class ConfigurationApiClient extends BaseClient {

    private static ConfigurationApiClient CLIENT;

    public static ConfigurationApiClient getInstance(CommonG common) {
        if (CLIENT == null) {
            CLIENT = new ConfigurationApiClient(common);
        }
        return CLIENT;
    }

    private ConfigurationApiClient(CommonG common) {
        super(common);
    }

    public BaseResponse getCentralConfiguration() throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/cct-configuration-api/central");
        Response response = get(url);
        return map(response);
    }

    public BaseResponse getCentralConfiguration(String path) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/cct-configuration-api/central?path=");
        url = url.concat(path);

        Response response = get(url);
        return map(response);
    }

    public BaseResponse getSchema() throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/cct-configuration-api/central/schema");
        Response response = get(url);
        return map(response);
    }

    public CalicoConfiguration getNetwork(String networkId) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/cct-configuration-api/network/");
        url = url.concat(networkId);

        Response response = get(url);
        return map(response, CalicoConfiguration.class);
    }

    public BaseResponseList<CalicoConfiguration> getAllNetworks() throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/cct-configuration-api/network/all");

        Response response = get(url);
        return mapList(response, CalicoConfiguration.class);
    }

    public BaseResponse getMesosConfiguration() throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/cct-configuration-api/mesos");
        Response response = get(url);
        return map(response);
    }

    public BaseResponse getMesosConfiguration(String path) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/cct-configuration-api/mesos?path=");
        url = url.concat(path);

        Response response = get(url);
        return map(response);
    }

    public CalicoConfiguration createNetwork(String data) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/cct-configuration-api/network");

        Response response = post(url, data);
        return map(response, CalicoConfiguration.class);
    }

    public CalicoConfiguration updateNetwork(String data) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/cct-configuration-api/network");

        Response response = put(url, data);
        return map(response, CalicoConfiguration.class);
    }

    public BaseResponse removeNetwork(String networkId) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443/service/cct-configuration-api/network/");
        url = url.concat(networkId);

        Response response = delete(url);
        return map(response);
    }
}
