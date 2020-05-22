package com.stratio.qa.models.cct.deployApi;

import com.stratio.qa.models.BaseResponse;

public class TearDownResponse extends BaseResponse {

    private int code;

    private String message;

    private String parent;

    private String path;

    private String requestId;

    private ConceptState state;

    private String uuid;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getParent() {
        return parent;
    }

    public String getPath() {
        return path;
    }

    public String getRequestId() {
        return requestId;
    }

    public ConceptState getState() {
        return state;
    }

    public String getUuid() {
        return uuid;
    }
}
