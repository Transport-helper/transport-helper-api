package com.transport.helper_api.dto;
import lombok.Data;

@Data
public class RouteRequest {
    private Long startLocationId;
    private Long endLocationId;
    private String modeOfTransport;
    private double estimatedCost;
    private double estimatedTravelTime;
	public Long getStartLocationId() {
		return startLocationId;
	}
	public void setStartLocationId(Long startLocationId) {
		this.startLocationId = startLocationId;
	}
	public Long getEndLocationId() {
		return endLocationId;
	}
	public void setEndLocationId(Long endLocationId) {
		this.endLocationId = endLocationId;
	}
	public String getModeOfTransport() {
		return modeOfTransport;
	}
	public void setModeOfTransport(String modeOfTransport) {
		this.modeOfTransport = modeOfTransport;
	}
	public double getEstimatedCost() {
		return estimatedCost;
	}
	public void setEstimatedCost(double estimatedCost) {
		this.estimatedCost = estimatedCost;
	}
	public double getEstimatedTravelTime() {
		return estimatedTravelTime;
	}
	public void setEstimatedTravelTime(double estimatedTravelTime) {
		this.estimatedTravelTime = estimatedTravelTime;
	}
    
    
}
