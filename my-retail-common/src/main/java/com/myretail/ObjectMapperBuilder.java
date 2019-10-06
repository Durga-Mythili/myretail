package com.myretail;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperBuilder {
    private boolean snakeCase = true;
    private boolean readUnkownEnumsAsNull = true;
    private boolean failOnUnkown = false;

    public ObjectMapperBuilder camelCase() {
        snakeCase = false;
        return this;
    }

    public ObjectMapper build() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnkown)
                .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, readUnkownEnumsAsNull);

        if (snakeCase) {
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        }

        return objectMapper;
    }
}
