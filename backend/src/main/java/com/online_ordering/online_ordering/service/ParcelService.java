package com.online_ordering.online_ordering.service;

import com.online_ordering.online_ordering.dto.ParcelRequest;
import com.online_ordering.online_ordering.entity.Parcel;
import com.online_ordering.online_ordering.repository.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParcelService {

    @Autowired
    private ParcelRepository parcelRepository;

    // Create a new parcel booking
    public Parcel createParcel(ParcelRequest request) {
        Parcel parcel = new Parcel();
        
        // Generate unique tracking number
        parcel.setTrackingNumber(generateTrackingNumber());
        
        // Set sender information
        parcel.setSenderName(request.getSenderName());
        parcel.setSenderEmail(request.getSenderEmail());
        parcel.setSenderPhone(request.getSenderPhone());
        parcel.setSenderAddress(request.getSenderAddress());
        
        // Set recipient information
        parcel.setRecipientName(request.getRecipientName());
        parcel.setRecipientEmail(request.getRecipientEmail());
        parcel.setRecipientPhone(request.getRecipientPhone());
        parcel.setRecipientAddress(request.getRecipientAddress());
        
        // Set parcel details
        parcel.setWeight(request.getWeight());
        parcel.setLength(request.getLength());
        parcel.setWidth(request.getWidth());
        parcel.setHeight(request.getHeight());
        parcel.setDescription(request.getDescription());
        parcel.setParcelType(request.getParcelType());
        parcel.setDeliveryType(request.getDeliveryType());
        
        // Calculate shipping cost based on weight and delivery type
        double shippingCost = calculateShippingCost(
            request.getWeight(), 
            request.getDeliveryType()
        );
        parcel.setShippingCost(shippingCost);
        
        // Set estimated delivery date
        parcel.setEstimatedDeliveryDate(
            calculateEstimatedDeliveryDate(request.getDeliveryType())
        );
        
        // Set initial status
        parcel.setStatus(Parcel.ParcelStatus.PENDING);
        
        return parcelRepository.save(parcel);
    }

    // Get all parcels
    public List<Parcel> getAllParcels() {
        return parcelRepository.findAll();
    }

    // Get parcel by ID
    public Optional<Parcel> getParcelById(Long id) {
        return parcelRepository.findById(id);
    }

    // Get parcel by tracking number
    public Optional<Parcel> getParcelByTrackingNumber(String trackingNumber) {
        return parcelRepository.findByTrackingNumber(trackingNumber);
    }

    // Get parcels by sender email
    public List<Parcel> getParcelsBySenderEmail(String email) {
        return parcelRepository.findBySenderEmail(email);
    }

    // Get parcels by recipient email
    public List<Parcel> getParcelsByRecipientEmail(String email) {
        return parcelRepository.findByRecipientEmail(email);
    }

    // Get parcels by user email (as sender or recipient)
    public List<Parcel> getParcelsByUserEmail(String email) {
        return parcelRepository.findBySenderEmailOrRecipientEmail(email, email);
    }

    // Get parcels by status
    public List<Parcel> getParcelsByStatus(Parcel.ParcelStatus status) {
        return parcelRepository.findByStatus(status);
    }

    // Update parcel status
    public Parcel updateParcelStatus(Long id, Parcel.ParcelStatus status) {
        Optional<Parcel> parcelOptional = parcelRepository.findById(id);
        if (parcelOptional.isPresent()) {
            Parcel parcel = parcelOptional.get();
            parcel.setStatus(status);
            
            // If delivered, set actual delivery date
            if (status == Parcel.ParcelStatus.DELIVERED) {
                parcel.setActualDeliveryDate(LocalDateTime.now());
            }
            
            return parcelRepository.save(parcel);
        }
        throw new RuntimeException("Parcel not found with id: " + id);
    }

    // Update parcel
    public Parcel updateParcel(Long id, ParcelRequest request) {
        Optional<Parcel> parcelOptional = parcelRepository.findById(id);
        if (parcelOptional.isPresent()) {
            Parcel parcel = parcelOptional.get();
            
            // Update sender information
            parcel.setSenderName(request.getSenderName());
            parcel.setSenderEmail(request.getSenderEmail());
            parcel.setSenderPhone(request.getSenderPhone());
            parcel.setSenderAddress(request.getSenderAddress());
            
            // Update recipient information
            parcel.setRecipientName(request.getRecipientName());
            parcel.setRecipientEmail(request.getRecipientEmail());
            parcel.setRecipientPhone(request.getRecipientPhone());
            parcel.setRecipientAddress(request.getRecipientAddress());
            
            // Update parcel details
            parcel.setWeight(request.getWeight());
            parcel.setLength(request.getLength());
            parcel.setWidth(request.getWidth());
            parcel.setHeight(request.getHeight());
            parcel.setDescription(request.getDescription());
            parcel.setParcelType(request.getParcelType());
            parcel.setDeliveryType(request.getDeliveryType());
            
            // Recalculate shipping cost
            double shippingCost = calculateShippingCost(
                request.getWeight(), 
                request.getDeliveryType()
            );
            parcel.setShippingCost(shippingCost);
            
            // Recalculate estimated delivery date
            parcel.setEstimatedDeliveryDate(
                calculateEstimatedDeliveryDate(request.getDeliveryType())
            );
            
            return parcelRepository.save(parcel);
        }
        throw new RuntimeException("Parcel not found with id: " + id);
    }

    // Delete parcel
    public void deleteParcel(Long id) {
        parcelRepository.deleteById(id);
    }

    // Helper method to generate tracking number
    private String generateTrackingNumber() {
        return "TRK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Helper method to calculate shipping cost
    private double calculateShippingCost(double weight, Parcel.DeliveryType deliveryType) {
        double baseCost = 5.0; // Base cost
        double weightCost = weight * 2.0; // $2 per kg
        
        double deliveryMultiplier = switch (deliveryType) {
            case STANDARD -> 1.0;
            case EXPRESS -> 1.5;
            case SAME_DAY -> 2.5;
            case OVERNIGHT -> 2.0;
        };
        
        return (baseCost + weightCost) * deliveryMultiplier;
    }

    // Helper method to calculate estimated delivery date
    private LocalDateTime calculateEstimatedDeliveryDate(Parcel.DeliveryType deliveryType) {
        LocalDateTime now = LocalDateTime.now();
        
        return switch (deliveryType) {
            case STANDARD -> now.plusDays(5);
            case EXPRESS -> now.plusDays(2);
            case SAME_DAY -> now.plusHours(8);
            case OVERNIGHT -> now.plusDays(1);
        };
    }
}

