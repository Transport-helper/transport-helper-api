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
import java.util.List;

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
        locations.add(locationRepository.
                findById(locationIds.getFirst()).
                orElseThrow(() -> new GlobalException("Location not found", HttpStatus.NOT_FOUND)));
        locations.
                add(locationRepository.findById(locationIds.getLast()).
                        orElseThrow(() -> new GlobalException("Location not found", HttpStatus.NOT_FOUND)));

        boolean exists = routeRepository.
                findRouteWithLocationsAndModeOfTransport(
                        locationIds.getFirst(), locationIds.getLast(), transportMode
                ).isPresent();

        if (exists) {
            throw new GlobalException("Route already exists", HttpStatus.CONFLICT);
        }

        Route newRoute = Route.builder()
                .modeOfTransport(transportMode)
                .estimatedCost(cost)
                .estimatedTravelTime(time)
                .validity(0.0)
                .locations(locations).build();

        Route createdRoute = routeRepository.save(newRoute);
        routeRepository.createRouteWithLocations(createdRoute.getId(), locationIds.getFirst(), locationIds.getLast());

        return createdRoute;
    }

    public List<Route> getAllRoutesForLocation(String locationId, Double price, String mode) {
        if (mode == null && price == null){
            return routeRepository.findByLocationsIdOrderByValidityDesc(locationId);
        } else if (mode == null) {
            return routeRepository.findByLocationsIdAndEstimatedCostBetweenOrderByValidityDesc(locationId,0.0, price);
        } else if (price == null) {
            return routeRepository.findByLocationsIdAndModeOfTransportOrderByValidityDesc(locationId, mode);
        } else {
            return routeRepository.findByLocationsIdAndModeOfTransportAndEstimatedCostBetweenOrderByValidityDesc(locationId, mode, 0.0, price);
        }
    }

    public List<Route> getAllRoutesConnectingTwoLocations(String loc1Id, String loc2Id, Double price, String mode) {
        return routeRepository.findRoutesConnectingTwoLocations(loc1Id, loc2Id, price, mode);
    }
}
