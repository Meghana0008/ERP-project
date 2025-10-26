package com.online_ordering.online_ordering.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parcels")
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tracking number is required")
    @Column(unique = true, nullable = false)
    private String trackingNumber;

    // Sender Information
    @NotBlank(message = "Sender name is required")
    @Column(nullable = false)
    private String senderName;

    @NotBlank(message = "Sender email is required")
    @Email(message = "Invalid sender email format")
    @Column(nullable = false)
    private String senderEmail;

    @NotBlank(message = "Sender phone is required")
    @Column(nullable = false)
    private String senderPhone;

    @NotBlank(message = "Sender address is required")
    @Column(nullable = false, length = 500)
    private String senderAddress;

    // Recipient Information
    @NotBlank(message = "Recipient name is required")
    @Column(nullable = false)
    private String recipientName;

    @NotBlank(message = "Recipient email is required")
    @Email(message = "Invalid recipient email format")
    @Column(nullable = false)
    private String recipientEmail;

    @NotBlank(message = "Recipient phone is required")
    @Column(nullable = false)
    private String recipientPhone;

    @NotBlank(message = "Recipient address is required")
    @Column(nullable = false, length = 500)
    private String recipientAddress;

    // Parcel Details
    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    @Column(nullable = false)
    private Double weight; // in kg

    @NotNull(message = "Length is required")
    @Positive(message = "Length must be positive")
    @Column(nullable = false)
    private Double length; // in cm

    @NotNull(message = "Width is required")
    @Positive(message = "Width must be positive")
    @Column(nullable = false)
    private Double width; // in cm

    @NotNull(message = "Height is required")
    @Positive(message = "Height must be positive")
    @Column(nullable = false)
    private Double height; // in cm

    @Column(length = 1000)
    private String description;

    @NotNull(message = "Parcel type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParcelType parcelType;

    // Delivery Information
    @NotNull(message = "Delivery type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryType deliveryType;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParcelStatus status;

    @Column(nullable = false)
    private Double shippingCost;

    private LocalDateTime estimatedDeliveryDate;

    private LocalDateTime actualDeliveryDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Constructors
    public Parcel() {
        this.status = ParcelStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Enums
    public enum ParcelType {
        DOCUMENT,
        PACKAGE,
        FRAGILE,
        PERISHABLE,
        ELECTRONICS
    }

    public enum DeliveryType {
        STANDARD,
        EXPRESS,
        SAME_DAY,
        OVERNIGHT
    }

    public enum ParcelStatus {
        PENDING,
        CONFIRMED,
        PICKED_UP,
        IN_TRANSIT,
        OUT_FOR_DELIVERY,
        DELIVERED,
        CANCELLED,
        RETURNED
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ParcelType getParcelType() {
        return parcelType;
    }

    public void setParcelType(ParcelType parcelType) {
        this.parcelType = parcelType;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public ParcelStatus getStatus() {
        return status;
    }

    public void setStatus(ParcelStatus status) {
        this.status = status;
    }

    public Double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public LocalDateTime getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(LocalDateTime estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public LocalDateTime getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(LocalDateTime actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

