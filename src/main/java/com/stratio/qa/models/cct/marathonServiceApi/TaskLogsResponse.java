package com.stratio.qa.models.cct.marathonServiceApi;

import com.stratio.qa.models.BaseResponse;

import java.util.Collection;
import java.util.List;

public class TaskLogsResponse extends BaseResponse {

    private List<String> links;

    private Collection<TaskLog> content;

    public List<String> getLinks() {
        return links;
    }

    public Collection<TaskLog> getContent() {
        return content;
    }
}
