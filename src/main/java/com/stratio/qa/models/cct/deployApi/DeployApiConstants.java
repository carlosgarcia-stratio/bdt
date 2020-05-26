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

import java.util.HashMap;
import java.util.Map;

public class DeployApiConstants {

    public static Map<String, String> statesDict = new HashMap<String, String>() { {
            put("running", "TASK_RUNNING");
            put("failed", "TASK_FAILED");
            put("finished", "TASK_FINISHED");
            put("staging", "TASK_STAGING");
            put("starting", "TASK_STARTING");
            put("killed", "TASK_KILLED");
        } };


}
