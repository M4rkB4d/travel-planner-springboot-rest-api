package com.example.travel_planner_springboot_rest_api.model;

import com.example.travel_planner_springboot_rest_api.util.DateTimeConverterUtil;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

@Document(collection = "bookings")
public class Booking {

    private static final DateTimeConverterUtil dateTimeConverterUtil = new DateTimeConverterUtil();

    public Booking() {}

    public Booking(
            String userId,
            String tripId,
            String bookingType,
            BookingDetails bookingDetails,
            String status
    ) {
        this.userId = userId;
        this.tripId = tripId;
        this.bookingType = bookingType;
        this.bookingDetails = bookingDetails;
        this.status = status;
    }

    public Booking(
            String userId,
            String tripId,
            String bookingType,
            BookingDetails bookingDetails,
            String status,
            Instant createdAt,
            Instant updatedAt
    )
    {
        this.userId = userId;
        this.tripId = tripId;
        this.bookingType = bookingType;
        this.bookingDetails = bookingDetails;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Id
    private String id;
    private String userId;
    private String tripId;
    private String bookingType;
    private BookingDetails bookingDetails;
    private String status;
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

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public BookingDetails getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(BookingDetails bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedAt() {
        return dateTimeConverterUtil.getZonedDateTime(this.createdAt);
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = dateTimeConverterUtil.getInstantDateTime(createdAt);
    }

    public ZonedDateTime getUpdatedAt() {
        return dateTimeConverterUtil.getZonedDateTime(updatedAt);
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = dateTimeConverterUtil.getInstantDateTime(updatedAt);
    }

    public static class BookingDetails {

        public BookingDetails() {}

        public BookingDetails(String provider, String confirmationNumber, double price) {
            this.provider = provider;
            this.confirmationNumber = confirmationNumber;
            this.price = price;
        }

        private String provider;
        private String confirmationNumber;
        private double price;

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public String getConfirmationNumber() {
            return confirmationNumber;
        }

        public void setConfirmationNumber(String confirmationNumber) {
            this.confirmationNumber = confirmationNumber;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
