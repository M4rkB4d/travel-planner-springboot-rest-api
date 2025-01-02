package com.example.travel_planner_springboot_rest_api.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ReadConcern;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.core.convert.converter.Converter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.example.travel_planner_springboot_rest_api.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    // ✅ 1. Database Name
    @Override
    protected @NotNull String getDatabaseName() {
        return "travel_planner";
    }

    // ✅ 2. MongoClient with Connection Pooling
    @Bean
    @Override
    public @NotNull MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/travel_planner");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(builder -> {
                    builder.maxSize(50); // Maximum number of connections
                    builder.minSize(10); // Minimum number of idle connections
                    builder.maxWaitTime(2000, TimeUnit.MILLISECONDS); // Max wait time for a connection
                    builder.maxConnectionIdleTime(5, TimeUnit.MINUTES); // Max idle time before closing
                })
                .readConcern(ReadConcern.MAJORITY)
                .writeConcern(WriteConcern.MAJORITY)
                .build();

        return MongoClients.create(settings);
    }

    // ✅ 3. Enable Auto Index Creation
    @Override
    public boolean autoIndexCreation() {
        return true;
    }

    // ✅ 4. Register Custom Converters
    @Bean
    @Override
    public @NotNull MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new ZonedDateTimeWriteConverter(),
                new ZonedDateTimeReadConverter()
        ));
    }
}

@WritingConverter
class ZonedDateTimeWriteConverter implements Converter<ZonedDateTime, Date> {
    @Override
    public Date convert(ZonedDateTime source) {
        return Date.from(source.toInstant());
    }
}

@ReadingConverter
class ZonedDateTimeReadConverter implements Converter<Date, ZonedDateTime> {
    @Override
    public ZonedDateTime convert(Date source) {
        return ZonedDateTime.ofInstant(source.toInstant(), ZoneId.of("Asia/Manila"));
    }
}