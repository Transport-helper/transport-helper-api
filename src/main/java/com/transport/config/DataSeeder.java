package com.transport.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transport.model.Location;
import com.transport.repository.LocationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final LocationRepository locationRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DataSeeder(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public void run(String... args) {
        try {
            var typeRef = new TypeReference<List<Location>>() {};
            var inputStream = new ClassPathResource("seeding/locations.json").getInputStream();
            List<Location> seedingLocations = objectMapper.readValue(inputStream, typeRef);

            System.out.println("Seeding locations...");
            var existingLocations = locationRepository.findAll().stream().map(Location::getName).toList();
            var newLocations = seedingLocations.stream()
                    .filter(location -> !existingLocations.contains(location.getName()))
                    .toList();

            for (Location location : newLocations) {
                System.out.println(location.getName());
            }

            locationRepository.saveAll(newLocations);
        } catch (IOException e) {
            System.err.println("Failed to seed locations: " + e.getMessage());
        }
    }
}
