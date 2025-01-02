package com.example.travel_planner_springboot_rest_api.service;

import com.example.travel_planner_springboot_rest_api.model.Trip;
import com.example.travel_planner_springboot_rest_api.repository.TripRepository;
import com.example.travel_planner_springboot_rest_api.dto.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    public ApiResponse<Trip> createTrip(Trip trip) {

        tripRepository.save(trip);
        return new ApiResponse<>(
                200,
                "Trip created",
                trip
        );
    }

    public Optional<ApiResponse<List<Trip>>> getTrips() {

        List<Trip> trips = tripRepository.findAll();
        if (trips.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(
                new ApiResponse<>(
                        200,
                        "Trips found",
                        trips
                )
        );
    }

    public Optional<ApiResponse<Optional<Trip>>> getTrip(String id) {

        Optional<Trip> trip = tripRepository.findById(id);
        if (trip.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(
                new ApiResponse<>(
                        200,
                        "Trip found",
                        trip
                )
        );
    }

    public Optional<ApiResponse<Trip>> updateTrip(String id, Trip updatedTrip) {

        Optional<Trip> trip = tripRepository.findById(id);
        if (trip.isEmpty()) {
            return Optional.empty();
        }
        updatedTrip.setId(trip.get().getId());
        updatedTrip.setCreatedAt(trip.get().getCreatedAt());
        tripRepository.save(updatedTrip);
        return Optional.of(
                new ApiResponse<>(
                        200,
                        "Trip updated",
                        updatedTrip
                )
        );
    }

    public Optional<ApiResponse<Optional<Trip>>> deleteTrip(String id) {

        Optional<Trip> trip = tripRepository.findById(id);
        if (trip.isEmpty()) {
            return Optional.empty();
        }
        tripRepository.deleteById(id);
        return Optional.of(
                new ApiResponse<>(
                        200,
                        "Trip deleted",
                        trip
                )
        );
    }
}
