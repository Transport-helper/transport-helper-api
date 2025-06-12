package com.transport.controller;

import com.transport.exceptions.GlobalException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.transport.dto.RouteResponse;
import com.transport.service.RouteService;

import com.transport.dto.RouteRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@Tag(name = "Routes", description = "Operations related to routes")

public class RouteController {
	private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @Operation(
            summary = "Add a new route",
            description = "Creates a new route between two locations with a specified transport mode and estimated data"
    )
    @PostMapping
    public ResponseEntity<RouteResponse> addRoute(@RequestBody @Valid RouteRequest request) throws GlobalException {
        RouteResponse route = routeService.addRoute(
            request.getLocationIds(),
            request.getModeOfTransport(),
            request.getEstimatedCost(),
            request.getEstimatedTravelTime()
        );

        return new ResponseEntity<>(route, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get routes for a location",
            description = "Get all routes starting/ending at a specific location"
    )
    @GetMapping("/{locationId}")
    public ResponseEntity<List<RouteResponse>> getRoutesForLocation(
            @PathVariable String locationId,
            @RequestParam(value = "mode", required = false) String mode,
            @RequestParam(value = "price",required = false) Double price
    ) {
        return ResponseEntity.ok(routeService.getAllRoutesForLocation(locationId,price,mode));
    }

    @Operation(
            summary = "Get routes between locations",
            description = "Get all routes between two specific locations"
    )
    @GetMapping()
    public ResponseEntity<List<RouteResponse>> getRoutesConnectingTwoLocations(
            @RequestParam("loc1") String location1,
            @RequestParam("loc2") String location2,
            @RequestParam(value = "mode", required = false) String mode,
            @RequestParam(value = "price",required = false) Double price
    ) {
        return ResponseEntity.ok(routeService.getAllRoutesConnectingTwoLocations(location1, location2,price, mode));
    }
}
