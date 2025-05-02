package com.transport.helper_api.repository;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.transport.helper_api.model.Location;

public interface LocationRepository extends Neo4jRepository<Location, Long> {

}
