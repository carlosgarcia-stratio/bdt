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
