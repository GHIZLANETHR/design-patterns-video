package org.ghizlane.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {

    private static final ObjectMapper MAPPER =
            new ObjectMapper();

    private JsonUtil() {
    }

    public static <T> String toJson(T object) {

        try {
            return MAPPER
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(object);

        } catch (JsonProcessingException e) {
            throw new IllegalStateException(
                    "Erreur de conversion JSON",
                    e
            );
        }
    }
}

