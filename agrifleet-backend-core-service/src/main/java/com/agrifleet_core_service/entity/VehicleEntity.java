package com.agrifleet_core_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicles")
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long vehicleId;

    @Column(name = "owner_id")
    private Long ownerId = 1L; // Default for simulation

    @Column(name = "vehicle_type", nullable = false)
    private String vehicleType;

    @Column(name = "specs")
    private String specs = "{}";

    @Column(name = "pricing")
    private String pricing = "{}";

    @Column(name = "rating")
    private Double rating = 5.0;

    @Column(name = "current_lat")
    private Double currentLat;

    @Column(name = "current_lng")
    private Double currentLng;

    @Column(name = "availability_status")
    private String availabilityStatus = "AVAILABLE";

    @Column(name = "created_at", insertable = false, updatable = false)
    private String createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private String updatedAt;

    // --- Getters and Setters ---
    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public String getSpecs() { return specs; }
    public void setSpecs(String specs) { this.specs = specs; }

    public String getPricing() { return pricing; }
    public void setPricing(String pricing) { this.pricing = pricing; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Double getCurrentLat() { return currentLat; }
    public void setCurrentLat(Double currentLat) { this.currentLat = currentLat; }

    public Double getCurrentLng() { return currentLng; }
    public void setCurrentLng(Double currentLng) { this.currentLng = currentLng; }

    public String getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
