package com.transport.dto;
import lombok.Data;

@Data
public class RouteRequest {
    private Long startLocationId;
    private Long endLocationId;
    private String modeOfTransport;
    private double estimatedCost;
    private double estimatedTravelTime;
}
