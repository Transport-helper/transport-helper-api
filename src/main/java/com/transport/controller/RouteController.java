package com.transport.controller;

import com.transport.exceptions.GlobalException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.transport.model.Route;
import com.transport.service.RouteService;

import com.transport.dto.RouteRequest;
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
    public ResponseEntity<Route> addRoute(@RequestBody @Valid RouteRequest request) throws GlobalException {
        Route savedRoute = routeService.addRoute(
            request.getLocationIds(),
            request.getModeOfTransport(),
            request.getEstimatedCost(),
            request.getEstimatedTravelTime()
        );

        return new ResponseEntity<>(savedRoute, HttpStatus.CREATED);
    }
}
