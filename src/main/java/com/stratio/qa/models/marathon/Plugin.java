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

import java.util.List;

public class Plugin {

    private String id;

    private String implementation;

    private Info info;

    private String plugin;

    private List<String> tags;

    public String getId() {
        return id;
    }

    public String getImplementation() {
        return implementation;
    }

    public Info getInfo() {
        return info;
    }

    public String getPlugin() {
        return plugin;
    }

    public List<String> getTags() {
        return tags;
    }

    public static class Info {

        private String version;

        private List<Object> array;

        private Boolean test;

        public String getVersion() {
            return version;
        }

        public List<Object> getArray() {
            return array;
        }

        public Boolean getTest() {
            return test;
        }
    }
}
