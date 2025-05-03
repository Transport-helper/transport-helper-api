package com.transport.service;

import org.springframework.stereotype.Service;

import com.transport.model.Location;
import com.transport.model.Route;
import com.transport.repository.LocationRepository;
import com.transport.repository.RouteRepository;

@Service
public class RouteService {
	private final RouteRepository routeRepository;

    private final LocationRepository locationRepository;

    public RouteService(RouteRepository routeRepository, LocationRepository locationRepository) {
        this.routeRepository = routeRepository;
        this.locationRepository = locationRepository;
    }

    public Route addRoute(Long startId, Long endId, String transportMode, double cost, double time) {
        Location start = locationRepository.findById(startId)
            .orElseThrow(() -> new IllegalArgumentException("Start location not found"));

        Location end = locationRepository.findById(endId)
            .orElseThrow(() -> new IllegalArgumentException("End location not found"));

        boolean exists = routeRepository
            .findByStartLocationIdAndEndLocationIdAndModeOfTransport(startId, endId, transportMode)
            .isPresent();

        if (exists) {
            throw new IllegalArgumentException("Route already exists");
        }

        Route newRoute = new Route();
        newRoute.setStartLocation(start);
        newRoute.setEndLocation(end);
        newRoute.setModeOfTransport(transportMode);
        newRoute.setEstimatedCost(cost);
        newRoute.setEstimatedTravelTime(time);
        newRoute.setValidity(null); 

        return routeRepository.save(newRoute);
    }
}
