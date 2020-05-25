package com.stratio.qa.models.cct.marathonServiceApi;

import com.stratio.qa.models.BaseResponse;

import java.util.Collection;
import java.util.List;

public class DeployedServicesResponse extends BaseResponse {

    private List<String> links;

    private Collection<DeployedService> content;

    public List<String> getLinks() {
        return links;
    }

    public Collection<DeployedService> getContent() {
        return content;
    }
}
