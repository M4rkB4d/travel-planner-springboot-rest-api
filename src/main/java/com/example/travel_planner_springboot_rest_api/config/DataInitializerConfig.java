package com.example.travel_planner_springboot_rest_api.config;

import com.example.travel_planner_springboot_rest_api.model.User;
import com.example.travel_planner_springboot_rest_api.repository.UserRepository;

import com.example.travel_planner_springboot_rest_api.model.Trip;
import com.example.travel_planner_springboot_rest_api.repository.TripRepository;

import com.example.travel_planner_springboot_rest_api.model.Booking;
import com.example.travel_planner_springboot_rest_api.repository.BookingRepository;

import com.example.travel_planner_springboot_rest_api.util.DateTimeConverterUtil;
import com.example.travel_planner_springboot_rest_api.util.JsonLoggerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
public class DataInitializerConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final DateTimeConverterUtil dateTimeConverterUtil = new DateTimeConverterUtil();

    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepository,
            TripRepository tripRepository,
            BookingRepository bookingRepository
    )
    {
        return args -> {

            if (userRepository.count() == 0 && tripRepository.count() == 0 && bookingRepository.count() == 0)
            {
                // Admins
                User admin = new User(
                        "admin",
                        "admin@example.com",
                        passwordEncoder.encode("admin123"),
                        "ADMIN"
                );
                User savedAdmin = userRepository.save(admin);
                JsonLoggerUtil.logAsJson(savedAdmin);
                System.out.println("✅ Sample Admins initialized.");

                // Users
                User user1 = new User(
                        "user1",
                        "user1@example.com",
                        passwordEncoder.encode("user1-123")
                );
                User savedUser1 = userRepository.save(user1);
                JsonLoggerUtil.logAsJson(savedUser1);
                System.out.println("✅ Sample Users initialized.");

                // Trips
                Trip trip1 = new Trip(
                        savedUser1.getId(),
                        "Test",
                        "Test",
                        dateTimeConverterUtil.getCurrentZonedDateTime(),
                        dateTimeConverterUtil.getCurrentZonedDateTime(),
                        Arrays.asList(
                                new Trip.Activity(
                                        "a",
                                        "b",
                                        dateTimeConverterUtil.getCurrentZonedDateTime()
                                ),
                                new Trip.Activity(
                                        "d",
                                        "e",
                                        dateTimeConverterUtil.getCurrentZonedDateTime()
                                )
                        ),
                        new Trip.Budget(
                                2000,
                                0
                        ),
                        true

                );
                Trip savedTrip1 = tripRepository.save(trip1);
                JsonLoggerUtil.logAsJson(savedTrip1);
                System.out.println("✅ Sample trips initialized.");


                // Bookings
                Booking booking1 = new Booking(
                        savedUser1.getId(),
                        savedTrip1.getId(),
                        "Test",
                        new Booking.BookingDetails(
                                "test",
                                "1",
                                1000
                        ),
                        "pending"
                );
                Booking savedBooking1 = bookingRepository.save(booking1);
                JsonLoggerUtil.logAsJson(savedBooking1);
                System.out.println("✅ Sample bookings initialized.");
            }
        };
    }
}
