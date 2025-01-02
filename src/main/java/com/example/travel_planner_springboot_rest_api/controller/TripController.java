package com.example.travel_planner_springboot_rest_api.controller;

import com.example.travel_planner_springboot_rest_api.service.TripService;
import com.example.travel_planner_springboot_rest_api.dto.ApiResponse;
import com.example.travel_planner_springboot_rest_api.model.Trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trip")
public class TripController {

    @Autowired
    private TripService tripService;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<Trip>> createTrip(@RequestBody Trip trip) {
        return ResponseEntity.ok(tripService.createTrip(trip));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Trip>>> getTrips() {
        return tripService.getTrips()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<Optional<Trip>>> getTrip(@PathVariable String id) {
        return tripService.getTrip(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<Trip>> updateTrip(@PathVariable String id, @RequestBody Trip trip) {
        return tripService.updateTrip(id, trip)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<Optional<Trip>>> deleteTrip(@PathVariable String id) {
        return tripService.deleteTrip(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}