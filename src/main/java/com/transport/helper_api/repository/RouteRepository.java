package com.transport.helper_api.repository;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.transport.helper_api.model.Route;
import java.util.Optional;

public interface RouteRepository extends Neo4jRepository<Route, Long> {
	
	Optional<Route> findByStartLocationIdAndEndLocationIdAndModeOfTransport(
		Long startLocationId, Long endLocationId, String modeOfTransport
	);
	
}
