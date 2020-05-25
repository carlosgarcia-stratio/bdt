package com.stratio.qa.models.marathon;

import com.stratio.qa.models.BaseResponse;

public class AppResponse extends BaseResponse {

    private VersionedApp app;

    public VersionedApp getApp() {
        return app;
    }
}
