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

    @Query("match (l1: Location {id: $loc1Id}) -[:CONNECTED_TO {modeOfTransport: $modeOfTransport}]- (l2: Location {id: $loc2Id})" +
            "return r")
    Optional<RouteResponse> findRouteWithLocationsAndModeOfTransport(String loc1Id, String loc2Id, String modeOfTransport);


    @Query("""
            MATCH (l1: Location {id: $loc1Id})
            MATCH (l2: Location {id: $loc2Id})
            CREATE (l1) -[r: CONNECTED_TO {modeOfTransport: $modeOfTransport, estimatedCost: $estimatedCost, estimatedTravelTime: $estimatedTravelTime, validity: 0}]-> (l2)
            RETURN r {.*, locations: [l1, l2]} AS route
            """)
    RouteResponse createRoute(String loc1Id,
                              String loc2Id,
                              String modeOfTransport,
                              Double estimatedCost,
                              Double estimatedTravelTime);

    @Query("""
            match (l:Location {id: $locId}) -[r:CONNECTED_TO]- (dest:Location)
            where r.estimatedCost >= coalesce($estimatedCost, 0.0)
            and (r.modeOfTransport = coalesce($modeOfTransport, r.modeOfTransport))
            return r {.*, locations: [l,dest]} as route
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

