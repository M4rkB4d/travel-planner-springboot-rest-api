package com.example.travel_planner_springboot_rest_api.dto;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ApiResponse<T> {
    private ZonedDateTime timeStamp;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private int statusCode;
    private String message;
    private T data;

    public ApiResponse(int statusCode, String message, T data) {
        timeStamp = ZonedDateTime.now(ZoneId.of("Asia/Manila"));
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}
