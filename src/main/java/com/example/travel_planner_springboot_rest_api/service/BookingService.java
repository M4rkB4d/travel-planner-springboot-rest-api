package com.example.travel_planner_springboot_rest_api.service;

import com.example.travel_planner_springboot_rest_api.model.Booking;
import com.example.travel_planner_springboot_rest_api.repository.BookingRepository;
import com.example.travel_planner_springboot_rest_api.dto.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public ApiResponse<Booking> createBooking(Booking booking) {

        bookingRepository.save(booking);
        return new ApiResponse<>(
                200,
                "Booking created",
                booking
        );
    }

    public Optional<ApiResponse<List<Booking>>> getBookings() {

        List<Booking> bookings = bookingRepository.findAll();
        if (bookings.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(
                new ApiResponse<>(
                        200,
                        "Bookings found",
                        bookings
                )
        );
    }

    public Optional<ApiResponse<Optional<Booking>>> getBooking(String id) {

        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(
                new ApiResponse<>(
                        200,
                        "Booking found",
                        booking
                )
        );
    }

    public Optional<ApiResponse<Booking>> updatedBooking(String id, Booking updatedBooking) {

        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()) {
            return Optional.empty();
        }
        updatedBooking.setId(booking.get().getId());
        updatedBooking.setCreatedAt(booking.get().getCreatedAt());
        bookingRepository.save(updatedBooking);
        return Optional.of(
                new ApiResponse<>(
                        200,
                        "Booking updated",
                        updatedBooking
                )
        );
    }

    public Optional<ApiResponse<Optional<Booking>>> deleteBooking(String id) {

        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()) {
            return Optional.empty();
        }
        bookingRepository.deleteById(id);
        return Optional.of(
                new ApiResponse<>(
                        200,
                        "Booking deleted",
                        booking
                )
        );
    }
}
