package com.transport.controller;

import com.transport.exceptions.GlobalException;
import com.transport.model.Location;
import com.transport.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@Tag(name = "Locations", description = "Operations related to locations")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(
            summary = "Get all locations",
            description = "Get all registered locations in the app"
    )
    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @Operation(
            summary = "Get location by name",
            description = "Get a location by exact name"
    )
    @GetMapping("exact")
    public ResponseEntity<Location> getLocationByExactName(@RequestParam("name") String name) throws GlobalException {
        return ResponseEntity.ok(locationService.findLocationByExactName(name));
    }

    @Operation(
            summary = "Search for a location",
            description = "Search for a location name through a list of names"
    )
    @GetMapping("search")
    public ResponseEntity<List<String>> searchForLocation(@RequestParam("name") String name) {
        return ResponseEntity.ok(locationService.searchForLocation(name));
    }

}
