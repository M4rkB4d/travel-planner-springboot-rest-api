package com.example.travel_planner_springboot_rest_api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonLoggerUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonLoggerUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static void logAsJson(Object object) {
        try {
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            logger.info("Object as JSON:\n{}", jsonString);
        } catch (JsonProcessingException e) {
            logger.error("Failed to convert object to JSON", e);
        }
    }
}
