package com.transport.service;

import com.transport.exceptions.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.transport.model.Location;
import com.transport.model.Route;
import com.transport.repository.LocationRepository;
import com.transport.repository.RouteRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RouteService {
	private final RouteRepository routeRepository;

    private final LocationRepository locationRepository;

    public RouteService(RouteRepository routeRepository, LocationRepository locationRepository) {
        this.routeRepository = routeRepository;
        this.locationRepository = locationRepository;
    }

    @Transactional
    public Route addRoute(List<String> locationIds, String transportMode, double cost, double time) throws GlobalException {
        if (locationIds.isEmpty()) {
            throw new GlobalException("No locations provided", HttpStatus.BAD_REQUEST);
        }

        List<Location> locations = new ArrayList<>();
        locations.add(locationRepository.findById(locationIds.getFirst()).orElseThrow(() -> new GlobalException("Location not found", HttpStatus.NOT_FOUND)));
        locations.add(locationRepository.findById(locationIds.getLast()).orElseThrow(() -> new GlobalException("Location not found", HttpStatus.NOT_FOUND)));

        boolean exists = routeRepository
            .findRouteWithLocationsAndModeOfTransport(locationIds.getFirst(),locationIds.getLast(), transportMode)
            .isPresent();

        if (exists) {
            throw new GlobalException("Route already exists", HttpStatus.CONFLICT);
        }

        Route newRoute = new Route();
        newRoute.setModeOfTransport(transportMode);
        newRoute.setEstimatedCost(cost);
        newRoute.setEstimatedTravelTime(time);
        newRoute.setValidity(null);
        newRoute.setLocations(locations);

        Route createdRoute = routeRepository.save(newRoute);
        routeRepository.createRouteWithLocations(createdRoute.getId(), locationIds.getFirst(), locationIds.getLast());

        return createdRoute;
    }
}
