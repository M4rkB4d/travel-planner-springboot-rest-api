package com.example.travel_planner_springboot_rest_api.repository;

import com.example.travel_planner_springboot_rest_api.model.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TripRepository extends MongoRepository<Trip, String> {
    Optional<Trip> findById(String id);
}
