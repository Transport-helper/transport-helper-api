package com.transport.service;

import com.transport.exceptions.GlobalException;
import com.transport.model.Location;
import com.transport.model.RouteResponse;
import com.transport.repository.LocationRepository;
import com.transport.repository.RouteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteRelationshipServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteService routeService;

    @Test
    void addRoute() throws GlobalException {
        String loc1Id = UUID.randomUUID().toString();
        String loc2Id = UUID.randomUUID().toString();
        when(locationRepository.findById(loc1Id)).thenReturn(Optional.of(new Location()));
        when(locationRepository.findById(loc2Id)).thenReturn(Optional.of(new Location()));
        RouteResponse savedConnectionRelationship = RouteResponse.builder()
                .id(UUID.randomUUID().toString())
                .locations(List.of(Location.builder().id(loc1Id).build(), Location.builder().id(loc2Id).build()))
                .build();

        when(routeRepository.save(any(RouteResponse.class))).thenReturn(savedConnectionRelationship);
        when(routeRepository.
                findRouteWithLocationsAndModeOfTransport(loc1Id, loc2Id, "Bus")).
                thenReturn(Optional.empty());

        RouteResponse result = routeService.addRoute(List.of(loc1Id, loc2Id), "Bus", 35, 47);
        assertNotNull(result);
        assertEquals(2, result.getLocations().size());
    }

    @Test
    void getAllRoutesForLocation() throws GlobalException {
        String loc1Id = UUID.randomUUID().toString();
        String loc2Id = UUID.randomUUID().toString();
        when(locationRepository.findById(loc1Id)).thenReturn(Optional.of(Location.builder().id(loc1Id).build()));
        when(locationRepository.findById(loc2Id)).thenReturn(Optional.of(Location.builder().id(loc2Id).build()));
        RouteResponse savedConnectionRelationship = RouteResponse.builder()
                .id(UUID.randomUUID().toString())
                .locations(List.of(Location.builder().id(loc1Id).build(), Location.builder().id(loc2Id).build()))
                .build();

        when(routeRepository.save(any(RouteResponse.class))).thenReturn(savedConnectionRelationship);
        when(routeRepository.findByLocationsIdAndModeOfTransportAndEstimatedCostBetweenOrderByValidityDesc(loc1Id, "", 0.0,0.0))
                .thenReturn(List.of(savedConnectionRelationship));

        routeService.addRoute(List.of(loc1Id, loc2Id), "Bus", 35, 47);

        List<RouteResponse> connectionRelationships = routeService.getAllRoutesForLocation(loc1Id,0.0,"");
        assertNotNull(connectionRelationships);
        assertEquals(1, connectionRelationships.size());
        assertEquals(connectionRelationships.getFirst().getLocations().getFirst().getId(), loc1Id);
        assertEquals(connectionRelationships.getLast().getLocations().getLast().getId(), loc2Id);
    }

    @Test
    void getAllRoutesConnectingTwoLocations() throws GlobalException {
        String loc1Id = UUID.randomUUID().toString();
        String loc2Id = UUID.randomUUID().toString();
        when(locationRepository.findById(loc1Id)).thenReturn(Optional.of(Location.builder().id(loc1Id).build()));
        when(locationRepository.findById(loc2Id)).thenReturn(Optional.of(Location.builder().id(loc2Id).build()));
        RouteResponse savedConnectionRelationship = RouteResponse.builder()
                .id(UUID.randomUUID().toString())
                .locations(List.of(Location.builder().id(loc1Id).build(), Location.builder().id(loc2Id).build()))
                .build();

        when(routeRepository.save(any(RouteResponse.class))).thenReturn(savedConnectionRelationship);

        routeService.addRoute(List.of(loc1Id, loc2Id), "Bus", 35, 47);
        routeService.addRoute(List.of(loc1Id, loc2Id), "Train", 35, 47);
        routeService.addRoute(List.of(loc1Id, loc2Id), "Taxi", 35, 47);

        when(routeRepository.findRoutesConnectingTwoLocations(loc1Id, loc2Id,0.0,""))
                .thenReturn(List.of(savedConnectionRelationship, savedConnectionRelationship, savedConnectionRelationship));

        List<RouteResponse> connectionRelationships = routeService.getAllRoutesConnectingTwoLocations(loc1Id,loc2Id,0.0,"");
        assertNotNull(connectionRelationships);
        assertEquals(3, connectionRelationships.size());
    }
}