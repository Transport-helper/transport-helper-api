package com.transport.helper_api.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Node("Location")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
	@Id
	private Long id;
	private String name;
	private double longitude;
	private double latitude;
}
