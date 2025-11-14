package ua.logistics.shipments.model;

import java.time.LocalDateTime;

public class Shipment {
    private Long id;
    private String origin;
    private String destination;
    private double weight; // тонн
    private String status; // PENDING, ASSIGNED, IN_TRANSIT, DELIVERED
    private Long assignedVehicleId;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;

    public Shipment() {}

    public Shipment(Long id, String origin, String destination, double weight, String status) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getAssignedVehicleId() { return assignedVehicleId; }
    public void setAssignedVehicleId(Long assignedVehicleId) { this.assignedVehicleId = assignedVehicleId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public void setDeliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; }
}