package com.transport.repository;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.transport.model.Route;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends Neo4jRepository<Route, String> {

	@Query("MATCH (r:Route)-[:CONNECTS]->(l:Location) " +
			"WHERE (l.id = $loc1Id OR l.id = $loc2Id) " +
			"WITH r, collect(l.id) AS locationIds " +
			"WHERE all(id IN [ $loc1Id, $loc2Id ] WHERE id IN locationIds) " +
			"AND (r.modeOfTransport = $modeOfTransport)" +
			"RETURN r")
	Optional<Route> findRouteWithLocationsAndModeOfTransport(String loc1Id, String loc2Id, String modeOfTransport);

	@Query("MATCH (r:Route), (l1:Location), (l2:Location) " +
			"WHERE r.id = $routeId AND l1.id = $loc1Id AND l2.id = $loc2Id " +
			"CREATE (r)-[:CONNECTS]->(l1), (r)-[:CONNECTS]->(l2)")
	void createRouteWithLocations(String routeId, String loc1Id, String loc2Id);

	List<Route> findByLocationsId_OrderByValidityDesc(String locationId);

	@Query("""
    MATCH (r:Route)-[:CONNECTS]->(l:Location)
    WHERE l.id IN [$loc1Id, $loc2Id]
    WITH r, collect(l.id) AS locIds
    WHERE all(id IN [$loc1Id, $loc2Id] WHERE id IN locIds)
    MATCH (r)-[:CONNECTS]->(loc:Location)
    RETURN DISTINCT r ORDER BY r.validity DESC
""")
	List<Route> findRoutesConnectingTwoLocations(String loc1Id, String loc2Id);

}
