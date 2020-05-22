package com.stratio.qa.models;

public class BaseResponse {

    private int hhtpStatus;

    private String rawResponse;

    public int getHttpStatus() {
        return hhtpStatus;
    }

    public void setHttpStatus(int hhtpStatus) {
        this.hhtpStatus = hhtpStatus;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }
}
