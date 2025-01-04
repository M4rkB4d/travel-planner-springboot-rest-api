package com.example.travel_planner_springboot_rest_api.repository;

import com.example.travel_planner_springboot_rest_api.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookingRepository extends MongoRepository<Booking, String> {
    Optional<Booking> findById(String id);
}
