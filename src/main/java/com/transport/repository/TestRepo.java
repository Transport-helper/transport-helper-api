package com.transport.repository;

import com.transport.model.TestNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepo extends Neo4jRepository<TestNode, Long> {
}
