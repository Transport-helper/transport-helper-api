package com.transport.dto;

import com.transport.model.Location;

public interface LocationProjection {
    String getId();
    String getName();
    Double getLatitude();
    Double getLongitude();

    default Location toLocation() {
        return Location
                .builder()
                .id(getId())
                .name(getName())
                .latitude(getLatitude())
                .longitude(getLongitude())
                .routeRelationship(null)
                .build();
    }
}
