package com.online_ordering.online_ordering;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class restApi {

    @GetMapping("/")
    public Map<String, Object> welcome() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to Online Parcel Booking API");
        response.put("version", "1.0.0");
        response.put("endpoints", Map.of(
            "parcels", "/api/parcels",
            "track", "/api/parcels/track/{trackingNumber}",
            "user_parcels", "/api/parcels/user/{email}"
        ));
        return response;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Online Parcel Booking API");
        return response;
    }
}
