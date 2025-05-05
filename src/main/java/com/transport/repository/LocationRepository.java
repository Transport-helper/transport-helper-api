package com.transport.repository;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.transport.model.Location;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends Neo4jRepository<Location, String> {
    Optional<Location> findByName(String name);
}
