package com.example.travel_planner_springboot_rest_api.util;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeConverterUtil {

    public ZonedDateTime getZonedDateTime(@NotNull Instant dateTime) {
        return dateTime.atZone(ZoneId.of("Asia/Manila"));
    }

    public Instant getInstantDateTime(@NotNull ZonedDateTime dateTime) {
        return dateTime.toInstant();
    }

    public ZonedDateTime getCurrentZonedDateTime() {
        return getZonedDateTime(Instant.now());
    }

    public Instant getCurrentInstantDateTime() {
        return getInstantDateTime(ZonedDateTime.now());
    }
}
