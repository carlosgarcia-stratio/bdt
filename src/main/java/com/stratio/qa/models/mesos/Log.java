package com.stratio.qa.models.mesos;

import com.stratio.qa.models.BaseResponse;

public class Log extends BaseResponse {

    private String data;

    private Long offset;

    public String getData() {
        return data;
    }

    public Long getOffset() {
        return offset;
    }
}
