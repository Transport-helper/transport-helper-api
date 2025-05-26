package com.transport.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RelationshipProperties
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteRelationship {
    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private Location location;

    private String modeOfTransport;
    private double estimatedCost;
    private double estimatedTravelTime;
    private Double validity;
}
