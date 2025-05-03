package com.transport.helper_api.repository;

import com.transport.helper_api.model.TestNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepo extends Neo4jRepository<TestNode, Long> {
}
