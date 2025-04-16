package com.transport.helper_api.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node
@Data
public class TestNode {
    @Id
    @GeneratedValue
    private Long id;
    @Property
    private String name;
}
