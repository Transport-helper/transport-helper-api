package com.transport.repository;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.transport.model.Route;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteRepository extends Neo4jRepository<Route, Long> {
	
	Optional<Route> findByStartLocationIdAndEndLocationIdAndModeOfTransport(
		Long startLocationId, Long endLocationId, String modeOfTransport
	);
	
}
