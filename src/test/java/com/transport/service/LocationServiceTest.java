package com.transport.service;

import com.transport.exceptions.GlobalException;
import com.transport.model.Location;
import com.transport.repository.LocationRepository;
import com.transport.utils.TrieSearch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    LocationRepository locationRepository;

    @Mock
    TrieSearch trieSearch;

    @InjectMocks
    LocationService locationService;

    @Test
    void getAllLocations() {
        assertNotNull(locationService.getAllLocations());
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    void findLocationByExactName_found() throws GlobalException {
        Location mockLocation = new Location("", "بنها", 95.22, 65.235);
        when(locationRepository.findByName("بنها")).thenReturn(Optional.of(mockLocation));

        Location location = locationService.findLocationByExactName("بنها");
        assertNotNull(location);
        assertEquals("بنها", location.getName());
        verify(locationRepository, times(1)).findByName("بنها");
    }

    @Test
    void findLocationByExactName_notFound() {
        GlobalException exception = assertThrows(GlobalException.class, () -> locationService.findLocationByExactName("<UNK>"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(locationRepository, times(1)).findByName("<UNK>");
    }

    @Test
    void searchForLocation() {
        when(trieSearch.search("بنهي", 2)).thenReturn(List.of("بنها"));
        when(trieSearch.search("نبها", 2)).thenReturn(List.of("بنها"));

        List<String> locations = locationService.searchForLocation("بنهي");
        assertNotNull(locations);
        assertEquals(List.of("بنها"), locations);
        verify(trieSearch, times(1)).search("بنهي", 2);

        locations = locationService.searchForLocation("نبها");
        assertNotNull(locations);
        assertEquals(List.of("بنها"), locations);
        verify(trieSearch, times(1)).search("نبها", 2);
    }
}