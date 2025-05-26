package com.transport.dto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class RouteRequest {
    private List<String> locationIds;
    private String modeOfTransport;
    private double estimatedCost;
    private double estimatedTravelTime;
}
