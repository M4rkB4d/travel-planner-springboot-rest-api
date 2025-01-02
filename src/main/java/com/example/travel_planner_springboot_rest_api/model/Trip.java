package com.example.travel_planner_springboot_rest_api.model;

import com.example.travel_planner_springboot_rest_api.util.DateTimeConverterUtil;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

@Document(collection = "trips")
public class Trip {

    private static final DateTimeConverterUtil dateTimeConverterUtil = new DateTimeConverterUtil();

    public Trip() {}

    public Trip(
            String userId,
            String title,
            String destination,
            ZonedDateTime startDate,
            ZonedDateTime endDate,
            List<Activity> activities,
            Budget budget,
            boolean isShared
    )
    {
        this.userId = userId;
        this.title = title;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activities = activities;
        this.budget = budget;
        this.isShared = isShared;
    }

    @Id
    private String id;
    private String userId;
    private String title;
    private String destination;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private List<Activity> activities;
    private Budget budget;
    private boolean isShared;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }

    public ZonedDateTime getCreatedAt() {
        return dateTimeConverterUtil.getZonedDateTime(this.createdAt);
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = dateTimeConverterUtil.getInstantDateTime(createdAt);
    }

    public ZonedDateTime getUpdatedAt() {
        return dateTimeConverterUtil.getZonedDateTime(this.updatedAt);
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = dateTimeConverterUtil.getInstantDateTime(updatedAt);
    }

    public static class Activity {

        private String activityName;
        private String location;
        private ZonedDateTime time;

        public Activity() {}

        public Activity(String activityName, String location, ZonedDateTime time) {
            this.activityName = activityName;
            this.location = location;
            this.time = time;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public ZonedDateTime getTime() {
            return time;
        }

        public void setTime(ZonedDateTime time) {
            this.time = time;
        }
    }

    public static class Budget {

        private double estimated;
        private double spent;

        public Budget() {}

        public Budget(double estimated, double spent) {
            this.estimated = estimated;
            this.spent = spent;
        }

        public double getEstimated() {
            return estimated;
        }

        public void setEstimated(double estimated) {
            this.estimated = estimated;
        }

        public double getSpent() {
            return spent;
        }

        public void setSpent(double spent) {
            this.spent = spent;
        }
    }
}

