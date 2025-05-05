package com.transport.dto;
import lombok.Data;

import java.util.List;

@Data
public class RouteRequest {
    private List<String> locationIds;
    private String modeOfTransport;
    private double estimatedCost;
    private double estimatedTravelTime;
}
