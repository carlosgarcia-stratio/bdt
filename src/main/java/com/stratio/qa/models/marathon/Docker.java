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

package com.stratio.qa.models.marathon;

import java.util.Collection;

public class Docker {

    private String image;

    private String network;

    private boolean forcePullImage;

    private Collection<Port> portMappings;

    private Collection<Parameter> parameters;

    private boolean privileged;

    private PullConfig pullConfig;

    public String getImage() {
        return image;
    }

    public String getNetwork() {
        return network;
    }

    public Collection<Port> getPortMappings() {
        return portMappings;
    }

    public boolean isPrivileged() {
        return privileged;
    }

    public Collection<Parameter> getParameters() {
        return parameters;
    }

    public boolean isForcePullImage() {
        return forcePullImage;
    }

    public PullConfig getPullConfig() {
        return this.pullConfig;
    }
}
