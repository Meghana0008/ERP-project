package com.online_ordering.online_ordering.repository;

import com.online_ordering.online_ordering.entity.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    
    Optional<Parcel> findByTrackingNumber(String trackingNumber);
    
    List<Parcel> findBySenderEmail(String senderEmail);
    
    List<Parcel> findByRecipientEmail(String recipientEmail);
    
    List<Parcel> findByStatus(Parcel.ParcelStatus status);
    
    List<Parcel> findBySenderEmailOrRecipientEmail(String senderEmail, String recipientEmail);
}

