package com.transport.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Node("Route")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {
	@Id
    @GeneratedValue
    private Long id;
    @Relationship(type = "STARTS_AT", direction = Relationship.Direction.OUTGOING)
    private Location startLocation;
    @Relationship(type = "ENDS_AT", direction = Relationship.Direction.OUTGOING)
    private Location endLocation;
    private String modeOfTransport;
    private double estimatedCost;
    private double estimatedTravelTime;
    private Double validity;
}
