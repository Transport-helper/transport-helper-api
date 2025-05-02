package com.transport.helper_api.model;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Location getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(Location startLocation) {
		this.startLocation = startLocation;
	}

	public Location getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(Location endLocation) {
		this.endLocation = endLocation;
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

	public Double getValidity() {
		return validity;
	}

	public void setValidity(Double validity) {
		this.validity = validity;
	}
    
    
    
    
}
