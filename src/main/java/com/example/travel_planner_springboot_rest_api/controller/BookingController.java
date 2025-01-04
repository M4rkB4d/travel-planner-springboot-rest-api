package com.example.travel_planner_springboot_rest_api.controller;

import com.example.travel_planner_springboot_rest_api.service.BookingService;
import com.example.travel_planner_springboot_rest_api.dto.ApiResponse;
import com.example.travel_planner_springboot_rest_api.model.Booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<Booking>> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Booking>>> getBookings() {
        return bookingService.getBookings()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<Optional<Booking>>> getBooking(@PathVariable String id) {
        return bookingService.getBooking(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<Booking>> updateBooking(@PathVariable String id, @RequestBody Booking trip) {
        return bookingService.updatedBooking(id, trip)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<Optional<Booking>>> deleteBooking(@PathVariable String id) {
        return bookingService.deleteBooking(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
