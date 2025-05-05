package com.transport.service;

import com.transport.exceptions.GlobalException;
import com.transport.model.Location;
import com.transport.repository.LocationRepository;
import com.transport.utils.FuzzySearch;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final FuzzySearch fuzzySearch;

    public LocationService(LocationRepository locationRepository, FuzzySearch fuzzySearch) {
        this.locationRepository = locationRepository;
        this.fuzzySearch = fuzzySearch;
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location findLocationByExactName(String name) throws GlobalException {
        return locationRepository.findByName(name).orElseThrow(
                () -> new GlobalException("Location not found", HttpStatus.NOT_FOUND)
        );
    }

    public List<String> searchForLocation(String name) {
        return fuzzySearch.search(name,name.length()/2);
    }
}
