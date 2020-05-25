package com.stratio.qa.models.marathon;

public class GetEventSubscriptionRegisterResponse {

    private String callbackUrl;

    private String clientIp;

    private String eventType;

    public String getCallbackUrl() {
        return this.callbackUrl;
    }

    public String getClientIp() {
        return this.clientIp;
    }

    public String getEventType() {
        return this.eventType;
    }
}
