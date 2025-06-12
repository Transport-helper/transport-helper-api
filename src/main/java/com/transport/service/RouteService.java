package com.transport.service;

import com.transport.exceptions.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.transport.model.Location;
import com.transport.dto.RouteResponse;
import com.transport.repository.LocationRepository;
import com.transport.repository.RouteRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RouteService {
    private final RouteRepository routeRepository;

    private final LocationRepository locationRepository;

    public RouteService(RouteRepository routeRepository, LocationRepository locationRepository) {
        this.routeRepository = routeRepository;
        this.locationRepository = locationRepository;
    }

    @Transactional
    public RouteResponse addRoute(List<String> locationIds, String transportMode, double cost, double time) throws GlobalException {
        if (locationIds.isEmpty()) {
            throw new GlobalException("No locations provided", HttpStatus.BAD_REQUEST);
        } else if(locationIds.size() > 2) {
            throw new GlobalException("Only 2 locations allowed", HttpStatus.BAD_REQUEST);
        }

        locationRepository
                .findById(locationIds.getFirst())
                .orElseThrow(() -> new GlobalException("Location not found", HttpStatus.NOT_FOUND));
        locationRepository
                .findById(locationIds.getLast())
                .orElseThrow(() -> new GlobalException("Location not found", HttpStatus.NOT_FOUND));

        boolean exists = routeRepository.findRouteWithLocationsAndModeOfTransport(locationIds.getFirst(), locationIds.getLast(), transportMode).isPresent();

        if (exists) {
            throw new GlobalException("Route already exists", HttpStatus.CONFLICT);
        }

        return routeRepository.createRoute(locationIds.getFirst(), locationIds.getLast(), transportMode, cost, time);
    }

    public List<RouteResponse> getAllRoutesForLocation(String locationId, Double price, String mode) {
        return routeRepository.searchRoutes(locationId, price, mode);
    }

    public List<RouteResponse> getAllRoutesConnectingTwoLocations(String loc1Id, String loc2Id, Double price, String mode) {
        return routeRepository.searchRoutesBetweenLocations(loc1Id, loc2Id, price, mode);
    }
}
