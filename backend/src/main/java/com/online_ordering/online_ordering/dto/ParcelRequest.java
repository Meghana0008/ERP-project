package com.online_ordering.online_ordering.dto;

import com.online_ordering.online_ordering.entity.Parcel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ParcelRequest {

    // Sender Information
    @NotBlank(message = "Sender name is required")
    private String senderName;

    @NotBlank(message = "Sender email is required")
    @Email(message = "Invalid sender email format")
    private String senderEmail;

    @NotBlank(message = "Sender phone is required")
    private String senderPhone;

    @NotBlank(message = "Sender address is required")
    private String senderAddress;

    // Recipient Information
    @NotBlank(message = "Recipient name is required")
    private String recipientName;

    @NotBlank(message = "Recipient email is required")
    @Email(message = "Invalid recipient email format")
    private String recipientEmail;

    @NotBlank(message = "Recipient phone is required")
    private String recipientPhone;

    @NotBlank(message = "Recipient address is required")
    private String recipientAddress;

    // Parcel Details
    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    private Double weight;

    @NotNull(message = "Length is required")
    @Positive(message = "Length must be positive")
    private Double length;

    @NotNull(message = "Width is required")
    @Positive(message = "Width must be positive")
    private Double width;

    @NotNull(message = "Height is required")
    @Positive(message = "Height must be positive")
    private Double height;

    private String description;

    @NotNull(message = "Parcel type is required")
    private Parcel.ParcelType parcelType;

    @NotNull(message = "Delivery type is required")
    private Parcel.DeliveryType deliveryType;

    // Constructors
    public ParcelRequest() {
    }

    // Getters and Setters
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

    public Parcel.ParcelType getParcelType() {
        return parcelType;
    }

    public void setParcelType(Parcel.ParcelType parcelType) {
        this.parcelType = parcelType;
    }

    public Parcel.DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Parcel.DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }
}

