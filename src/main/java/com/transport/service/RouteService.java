package com.transport.service;

import com.transport.exceptions.BaseException;
import com.transport.exceptions.DuplicateRouteException;
import com.transport.exceptions.LocationNotFoundException;
import org.springframework.http.HttpStatus;
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

    public Route addRoute(Long startId, Long endId, String transportMode, double cost, double time) throws BaseException {
        Location start = locationRepository.findById(startId)
            .orElseThrow(() -> new LocationNotFoundException("Starting location not found", HttpStatus.BAD_REQUEST));

        Location end = locationRepository.findById(endId)
            .orElseThrow(() -> new LocationNotFoundException("Ending location not found", HttpStatus.BAD_REQUEST));

        boolean exists = routeRepository
            .findByStartLocationIdAndEndLocationIdAndModeOfTransport(startId, endId, transportMode)
            .isPresent();

        if (exists) {
            throw new DuplicateRouteException("Route already exists", HttpStatus.CONFLICT);
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
