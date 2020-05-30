package com.stratio.qa.models.mesos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.stratio.qa.models.marathon.ExternalVolume;
import com.stratio.qa.models.marathon.LocalVolume;
import com.stratio.qa.models.marathon.PersistentLocalVolume;

import java.io.IOException;

public class Volume {

    @JsonProperty("host_path")
    private String hostPath;

    @JsonProperty("container_path")
    private String containerPath;

    private String mode;

    public String getHostPath() {
        return hostPath;
    }

    public String getContainerPath() {
        return containerPath;
    }

    public String getMode() {
        return mode;
    }

    public static class VolumeAdapterDeserializer extends StdDeserializer<com.stratio.qa.models.marathon.Volume> {

        public VolumeAdapterDeserializer() {
            this(null);
        }

        public VolumeAdapterDeserializer(Class<?> t) {
            super(t);
        }

        @Override
        public com.stratio.qa.models.marathon.Volume deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode node = jp.getCodec().readTree(jp);
            ObjectMapper mapper = new ObjectMapper();
            if (node.has("external")) {
                return mapper.readValue(node.toString(), ExternalVolume.class);
            } else if (node.has("persistent")) {
                return mapper.readValue(node.toString(), PersistentLocalVolume.class);
            } else {
                return mapper.readValue(node.toString(), LocalVolume.class);
            }
        }
    }
}
