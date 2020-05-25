/*
 * Copyright (C) 2014 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
