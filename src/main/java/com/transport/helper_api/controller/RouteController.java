package com.transport.helper_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.transport.helper_api.model.Route;
import com.transport.helper_api.service.RouteService;

import com.transport.helper_api.dto.RouteRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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
    public ResponseEntity<Route> addRoute(@RequestBody RouteRequest request) {
        Route savedRoute = routeService.addRoute(
            request.getStartLocationId(),
            request.getEndLocationId(),
            request.getModeOfTransport(),
            request.getEstimatedCost(),
            request.getEstimatedTravelTime()
        );

        return new ResponseEntity<>(savedRoute, HttpStatus.CREATED);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
