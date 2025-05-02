package com.transport.helper_api.service;

import org.springframework.stereotype.Service;

import com.transport.helper_api.model.Location;
import com.transport.helper_api.model.Route;
import com.transport.helper_api.repository.LocationRepository;
import com.transport.helper_api.repository.RouteRepository;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RouteService {
	@Autowired
    private RouteRepository routeRepository;

    @Autowired
    private LocationRepository locationRepository;

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
