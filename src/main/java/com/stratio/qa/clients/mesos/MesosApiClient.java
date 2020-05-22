package com.stratio.qa.clients.mesos;

import com.ning.http.client.Response;
import com.stratio.qa.clients.BaseClient;
import com.stratio.qa.models.mesos.Log;
import com.stratio.qa.models.mesos.MesosStateSummary;
import com.stratio.qa.specs.CommonG;
import com.stratio.qa.utils.ThreadProperty;

import java.util.Map;

public class MesosApiClient extends BaseClient {

    private static MesosApiClient CLIENT;

    public static MesosApiClient getInstance(CommonG common) {
        if (CLIENT == null) {
            CLIENT = new MesosApiClient(common);
        }
        return CLIENT;
    }

    private MesosApiClient(CommonG common) {
        super(common);
    }

    public void withContext(CommonG common) {
        this.cookies = common.getCookies();
    }

    public Log getLogs(String path, String logType, Map<String, String> queryParams) throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443");
        String endpoint = url.concat(path).concat(logType);

        if (queryParams != null) {
            for (Map.Entry<String, String> param: queryParams.entrySet()) {
                endpoint = endpoint.concat("&").concat(param.getKey()).concat("=").concat(param.getValue());
            }
        }

        Response response = get(endpoint);
        return map(response, Log.class);
    }

    public MesosStateSummary getStateSummary() throws Exception {
        String url = "https://".concat(ThreadProperty.get("EOS_ACCESS_POINT")).concat(":443").concat("/mesos/state-summary");

        Response response = get(url);
        return map(response, MesosStateSummary.class);
    }


}
