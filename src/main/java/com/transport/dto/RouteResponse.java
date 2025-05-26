package com.transport.dto;

import com.transport.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponse {
    private String id;
    private List<Location> locations;
    private String modeOfTransport;
    private double estimatedCost;
    private double estimatedTravelTime;
    private Double validity;
}
