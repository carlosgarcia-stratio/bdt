package com.stratio.qa.models.mesos;

import com.stratio.qa.models.BaseResponse;

import java.util.List;

public class MesosTasksResponse extends BaseResponse {

    private List<MesosTask> tasks;

    public List<MesosTask> getTasks() {
        return tasks;
    }
}
