package com.transport.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Node("Location")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
	@Id
	private String id = UUID.randomUUID().toString();
	private String name;
	private double longitude;
	private double latitude;
}