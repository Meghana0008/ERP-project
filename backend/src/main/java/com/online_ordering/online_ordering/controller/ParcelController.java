package com.online_ordering.online_ordering.controller;

import com.online_ordering.online_ordering.dto.ParcelRequest;
import com.online_ordering.online_ordering.entity.Parcel;
import com.online_ordering.online_ordering.service.ParcelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parcels")
@CrossOrigin(origins = "*")
public class ParcelController {

    @Autowired
    private ParcelService parcelService;

    // Create a new parcel booking
    @PostMapping
    public ResponseEntity<?> createParcel(@Valid @RequestBody ParcelRequest request) {
        try {
            Parcel parcel = parcelService.createParcel(request);
            return new ResponseEntity<>(parcel, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(
                new ErrorResponse("Failed to create parcel: " + e.getMessage()),
                HttpStatus.BAD_REQUEST
            );
        }
    }

    // Get all parcels
    @GetMapping
    public ResponseEntity<List<Parcel>> getAllParcels() {
        List<Parcel> parcels = parcelService.getAllParcels();
        return new ResponseEntity<>(parcels, HttpStatus.OK);
    }

    // Get parcel by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getParcelById(@PathVariable Long id) {
        Optional<Parcel> parcel = parcelService.getParcelById(id);
        if (parcel.isPresent()) {
            return new ResponseEntity<>(parcel.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(
            new ErrorResponse("Parcel not found with id: " + id),
            HttpStatus.NOT_FOUND
        );
    }

    // Track parcel by tracking number
    @GetMapping("/track/{trackingNumber}")
    public ResponseEntity<?> trackParcel(@PathVariable String trackingNumber) {
        Optional<Parcel> parcel = parcelService.getParcelByTrackingNumber(trackingNumber);
        if (parcel.isPresent()) {
            return new ResponseEntity<>(parcel.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(
            new ErrorResponse("Parcel not found with tracking number: " + trackingNumber),
            HttpStatus.NOT_FOUND
        );
    }

    // Get parcels by sender email
    @GetMapping("/sender/{email}")
    public ResponseEntity<List<Parcel>> getParcelsBySender(@PathVariable String email) {
        List<Parcel> parcels = parcelService.getParcelsBySenderEmail(email);
        return new ResponseEntity<>(parcels, HttpStatus.OK);
    }

    // Get parcels by recipient email
    @GetMapping("/recipient/{email}")
    public ResponseEntity<List<Parcel>> getParcelsByRecipient(@PathVariable String email) {
        List<Parcel> parcels = parcelService.getParcelsByRecipientEmail(email);
        return new ResponseEntity<>(parcels, HttpStatus.OK);
    }

    // Get parcels by user email (as sender or recipient)
    @GetMapping("/user/{email}")
    public ResponseEntity<List<Parcel>> getParcelsByUser(@PathVariable String email) {
        List<Parcel> parcels = parcelService.getParcelsByUserEmail(email);
        return new ResponseEntity<>(parcels, HttpStatus.OK);
    }

    // Get parcels by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Parcel>> getParcelsByStatus(@PathVariable Parcel.ParcelStatus status) {
        List<Parcel> parcels = parcelService.getParcelsByStatus(status);
        return new ResponseEntity<>(parcels, HttpStatus.OK);
    }

    // Update parcel status
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateParcelStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request) {
        try {
            Parcel parcel = parcelService.updateParcelStatus(id, request.getStatus());
            return new ResponseEntity<>(parcel, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                new ErrorResponse(e.getMessage()),
                HttpStatus.NOT_FOUND
            );
        }
    }

    // Update parcel
    @PutMapping("/{id}")
    public ResponseEntity<?> updateParcel(
            @PathVariable Long id,
            @Valid @RequestBody ParcelRequest request) {
        try {
            Parcel parcel = parcelService.updateParcel(id, request);
            return new ResponseEntity<>(parcel, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                new ErrorResponse(e.getMessage()),
                HttpStatus.NOT_FOUND
            );
        }
    }

    // Delete parcel
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteParcel(@PathVariable Long id) {
        try {
            parcelService.deleteParcel(id);
            return new ResponseEntity<>(
                new SuccessResponse("Parcel deleted successfully"),
                HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                new ErrorResponse("Failed to delete parcel: " + e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // Inner classes for responses
    static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

    static class SuccessResponse {
        private String message;

        public SuccessResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    static class StatusUpdateRequest {
        private Parcel.ParcelStatus status;

        public Parcel.ParcelStatus getStatus() {
            return status;
        }

        public void setStatus(Parcel.ParcelStatus status) {
            this.status = status;
        }
    }
}

