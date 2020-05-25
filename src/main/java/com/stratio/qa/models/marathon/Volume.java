package com.stratio.qa.models.marathon;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class Volume {

    private String hostPath;

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

    public static class VolumeDeserializer extends StdDeserializer<Volume> {

        public VolumeDeserializer() {
            this(null);
        }

        public VolumeDeserializer(Class<?> t) {
            super(t);
        }

        @Override
        public Volume deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
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
