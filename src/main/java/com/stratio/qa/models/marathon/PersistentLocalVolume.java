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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PersistentLocalVolume extends Volume {

    @JsonProperty("persistent")
    PersistentLocalVolumeInfo persistentLocalVolumeInfo;

    public PersistentLocalVolumeInfo getPersistentLocalVolumeInfo() {
        return persistentLocalVolumeInfo;
    }

    static class PersistentLocalVolumeInfo {

        public PersistentLocalVolumeInfo() { }

        public String type;

        private Integer size;

        public List<String> constraints;

        public String getType() {
            return type;
        }

        public List<String> getConstraints() {
            return constraints;
        }

        public Integer getSize() {
            return size;
        }
    }

}
