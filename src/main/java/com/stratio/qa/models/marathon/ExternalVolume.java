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

public class ExternalVolume extends Volume {

    @JsonProperty("external")
    private ExternalVolumeInfo externalVolumeInfo;

    public ExternalVolume() {
        this.externalVolumeInfo = new ExternalVolumeInfo();
    }

    ExternalVolumeInfo getExternalVolumeInfo() {
        return externalVolumeInfo;
    }

    /*
        Proxies
    */
    public String getName() {
        return this.externalVolumeInfo.name;
    }

    public String getProvider() {
        return this.externalVolumeInfo.provider;
    }

    public String getDriver() {
        return this.externalVolumeInfo.options.driver;
    }

    public Integer getOptSize() {
        return this.externalVolumeInfo.options.size;
    }

    public Integer getOptIops() {
        return this.externalVolumeInfo.options.iops;
    }

    public String getOptVolumeType() {
        return this.externalVolumeInfo.options.volumeType;
    }

    public String getOptNewFsType() {
        return this.externalVolumeInfo.options.newFsType;
    }

    public Boolean getOptOverwriteFs() {
        return this.externalVolumeInfo.options.overwriteFs;
    }

    class ExternalVolumeInfo {

        private String name;

        private Integer size;

        private String provider = "dvdi";

        private Options options;

        ExternalVolumeInfo() {
            this.options = new Options();
        }

        String getName() {
            return name;
        }

        Integer getSize() {
            return size;
        }

        String getProvider() {
            return provider;
        }

        Options getOptions() {
            return options;
        }
    }

    class Options {

        @JsonProperty("dvdi/driver")
        private String driver;

        private Integer size;

        @JsonProperty("IOPS")
        private Integer iops;

        private String volumeType;

        private String newFsType;

        private Boolean overwriteFs;

        String getDriver() {
            return driver;
        }

        Integer getSize() {
            return size;
        }

        Integer getIops() {
            return iops;
        }

        String getVolumeType() {
            return volumeType;
        }

        String getNewFsType() {
            return newFsType;
        }

        Boolean getOverwriteFs() {
            return overwriteFs;
        }
    }
}
