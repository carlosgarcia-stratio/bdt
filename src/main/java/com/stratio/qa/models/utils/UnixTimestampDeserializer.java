package com.stratio.qa.models.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UnixTimestampDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {
        String unixTimestamp = parser.getText().trim();
        try {
            if ("0".equals(unixTimestamp) || "0.0".equals(unixTimestamp)) {
                return null;
            } else {
                return new Date(TimeUnit.SECONDS.toMillis(Double.valueOf(unixTimestamp).intValue()));
            }
        } catch (Exception e) {
            return new Date(-1L);
        }
    }
}
