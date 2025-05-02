package com.transport.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transport.helper_api.controller.RouteController;
import com.transport.helper_api.dto.RouteRequest;
import com.transport.helper_api.model.Route;
import com.transport.helper_api.service.RouteService;
import org.springframework.http.MediaType;

@WebMvcTest(RouteController.class)
public class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RouteService routeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddRouteSuccess() throws Exception {
        RouteRequest request = new RouteRequest();
        request.setStartLocationId(1L);
        request.setEndLocationId(2L);
        request.setModeOfTransport("Bus");
        request.setEstimatedCost(10.0);
        request.setEstimatedTravelTime(20.0);

        Route mockRoute = new Route();
        
        when(routeService.addRoute(
                request.getStartLocationId(),
                request.getEndLocationId(),
                request.getModeOfTransport(),
                request.getEstimatedCost(),
                request.getEstimatedTravelTime()
        )).thenReturn(mockRoute);

        mockMvc.perform(post("/api/routes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
    
    @Test
    void testAddDuplicateRoute_shouldReturnBadRequest() throws Exception {
        RouteRequest request = new RouteRequest();
        request.setStartLocationId(1L);
        request.setEndLocationId(2L);
        request.setModeOfTransport("Bus");
        request.setEstimatedCost(10.0);
        request.setEstimatedTravelTime(20.0);

        doThrow(new IllegalArgumentException("Route already exists"))
            .when(routeService).addRoute(
                request.getStartLocationId(),
                request.getEndLocationId(),
                request.getModeOfTransport(),
                request.getEstimatedCost(),
                request.getEstimatedTravelTime()
            );

        mockMvc.perform(post("/api/routes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Route already exists"));
    }

    @Test
    void testAddRoute_locationNotFound_shouldReturnBadRequest() throws Exception {
        RouteRequest request = new RouteRequest();
        request.setStartLocationId(50L); 
        request.setEndLocationId(3L);
        request.setModeOfTransport("Car");
        request.setEstimatedCost(10.0);
        request.setEstimatedTravelTime(20.0);

        doThrow(new IllegalArgumentException("Start location not found"))
            .when(routeService).addRoute(
                request.getStartLocationId(),
                request.getEndLocationId(),
                request.getModeOfTransport(),
                request.getEstimatedCost(),
                request.getEstimatedTravelTime()
            );

        mockMvc.perform(post("/api/routes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Start location not found"));
    }
    
    @Test
    void testAddRoute_success_shouldReturnCreatedWithResponseBody() throws Exception {
        RouteRequest request = new RouteRequest();
        request.setStartLocationId(1L);
        request.setEndLocationId(2L);
        request.setModeOfTransport("Metro");
        request.setEstimatedCost(15.0);
        request.setEstimatedTravelTime(25.0);

        Route mockRoute = new Route();
        mockRoute.setId(1L);
        mockRoute.setModeOfTransport("Metro");
        mockRoute.setEstimatedCost(15.0);
        mockRoute.setEstimatedTravelTime(25.0);

        when(routeService.addRoute(
                request.getStartLocationId(),
                request.getEndLocationId(),
                request.getModeOfTransport(),
                request.getEstimatedCost(),
                request.getEstimatedTravelTime()
        )).thenReturn(mockRoute);

        mockMvc.perform(post("/api/routes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.modeOfTransport").value("Metro"))
                .andExpect(jsonPath("$.estimatedCost").value(15.0))
                .andExpect(jsonPath("$.estimatedTravelTime").value(25.0));
    }

}
