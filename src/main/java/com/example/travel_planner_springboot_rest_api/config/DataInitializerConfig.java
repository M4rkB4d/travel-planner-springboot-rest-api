package com.example.travel_planner_springboot_rest_api.config;

import com.example.travel_planner_springboot_rest_api.model.Trip;
import com.example.travel_planner_springboot_rest_api.repository.TripRepository;
import com.example.travel_planner_springboot_rest_api.util.DateTimeConverterUtil;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataInitializerConfig {

    private static final DateTimeConverterUtil dateTimeConverterUtil = new DateTimeConverterUtil();

    @Bean
    CommandLineRunner initDatabase(TripRepository tripRepository) {
        return args -> {

            if (tripRepository.count() == 0) {
                Trip trip1 = new Trip(
                        "1",
                        "Test",
                        "Test",
                        dateTimeConverterUtil.getCurrentDateTime(),
                        dateTimeConverterUtil.getCurrentDateTime(),
                        Arrays.asList(
                                new Trip.Activity(
                                        "a",
                                        "b",
                                        dateTimeConverterUtil.getCurrentDateTime()
                                ),
                                new Trip.Activity(
                                        "d",
                                        "e",
                                        dateTimeConverterUtil.getCurrentDateTime()
                                )
                        ),
                        new Trip.Budget(
                                2000,
                                0
                        ),
                        true
                );
                tripRepository.save(trip1);

                System.out.println("✅ Sample trips initialized.");
            }
        };
    }
}
