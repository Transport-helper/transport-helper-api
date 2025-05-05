package com.transport.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Node("Route")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    @Id
    private String id = UUID.randomUUID().toString();
    private String modeOfTransport;
    private double estimatedCost;
    private double estimatedTravelTime;
    private Double validity;

    @Transient
    private List<Location> locations;
}
