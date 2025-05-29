package com.transport.repository;

import com.transport.dto.RouteResponse;
import com.transport.model.RouteRelationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends Neo4jRepository<RouteRelationship, Long> {

    @Query("MATCH (l1:Location {id: $loc1Id})-[r:CONNECTED_TO {modeOfTransport: $modeOfTransport}]-(l2:Location {id: $loc2Id}) " +
            "RETURN r")
    Optional<RouteResponse> findRouteWithLocationsAndModeOfTransport(String loc1Id, String loc2Id, String modeOfTransport);


    @Query("""
            MATCH (l1: Location {id: $loc1Id})
            MATCH (l2: Location {id: $loc2Id})
            CREATE (l1) -[r: CONNECTED_TO {id: randomUUID(),modeOfTransport: $modeOfTransport, estimatedCost: $estimatedCost, estimatedTravelTime: $estimatedTravelTime, validity: 0}]-> (l2)
            RETURN r.id AS routeId, [l1, l2] AS locations, r.modeOfTransport AS modeOfTransport, 
                   r.estimatedCost AS estimatedCost, r.estimatedTravelTime AS estimatedTravelTime, 
                   r.validity AS validity
            """)
    RouteResponse createRoute(String loc1Id,
                              String loc2Id,
                              String modeOfTransport,
                              Double estimatedCost,
                              Double estimatedTravelTime);

    @Query("""
            MATCH (l:Location {id: $locId}) -[r:CONNECTED_TO]- (dest:Location)
            WHERE ($estimatedCost IS NULL OR r.estimatedCost >= $estimatedCost)
              AND ($modeOfTransport IS NULL OR r.modeOfTransport = $modeOfTransport)
            RETURN r.id AS routeId, [l, dest] AS locations, r.modeOfTransport AS modeOfTransport,
                   r.estimatedCost AS estimatedCost, r.estimatedTravelTime AS estimatedTravelTime,
                   r.validity AS validity
            """)
    List<RouteResponse> searchRoutes(String locId, Double estimatedCost, String modeOfTransport);

    @Query("""
            match (l1:Location {id: loc1Id}) -[r:CONNECTED_TO*]- (l2:Location {id: loc2Id})
            where r.estimatedCost >= coalesce($estimatedCost, 0.0)
            and (r.modeOfTransport = coalesce($modeOfTransport, r.modeOfTransport))
            return r {.*, locations: [l1,l2]} as route
            """)
    List<RouteResponse> searchRoutesBetweenLocations(String loc1Id, String loc2Id, Double estimatedCost, String modeOfTransport);
}