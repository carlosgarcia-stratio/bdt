package com.stratio.qa.clients.marathon;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ning.http.client.Response;
import com.stratio.qa.clients.BaseClient;
import com.stratio.qa.models.marathon.AppResponse;
import com.stratio.qa.models.marathon.AppsResponse;
import com.stratio.qa.models.marathon.Volume;
import com.stratio.qa.specs.CommonG;
import com.stratio.qa.utils.ThreadProperty;

import java.nio.file.Files;
import java.nio.file.Paths;

public class MarathonApiClient extends BaseClient {

    private static MarathonApiClient CLIENT;

    public static MarathonApiClient getInstance(CommonG common) {
        if (CLIENT == null) {
            CLIENT = new MarathonApiClient(common);
        }
        return CLIENT;
    }

    private MarathonApiClient(CommonG common) {
        super(common);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Volume.class, new Volume.VolumeDeserializer());
        mapper.registerModule(module);
    }

    public AppsResponse getApps() throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443").concat("/marathon/v2/apps");

        Response response = get(url);
        return map(response, AppsResponse.class);
    }

    public AppResponse getApp(String appId) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443").concat("/marathon/v2/apps/");
        url = url.concat(appId);

        Response response = get(url);
        return map(response, AppResponse.class);
    }
}
