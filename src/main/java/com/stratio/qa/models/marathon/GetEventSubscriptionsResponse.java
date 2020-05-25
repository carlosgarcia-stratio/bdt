package com.stratio.qa.models.marathon;

import java.util.ArrayList;
import java.util.List;

public class GetEventSubscriptionsResponse {

    private List<String> callbackUrls = new ArrayList<>();

    public List<String> getCallbackUrls() {
        return callbackUrls;
    }
}
