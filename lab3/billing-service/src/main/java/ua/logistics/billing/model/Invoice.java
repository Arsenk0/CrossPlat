package ua.logistics.billing.model;

import java.time.LocalDateTime;

public class Invoice {
    private Long id;
    private Long shipmentId;
    private double amount;
    private String currency;
    private String status; // PENDING, PAID, CANCELLED
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;

    public Invoice() {}

    public Invoice(Long id, Long shipmentId, double amount, String currency, String status) {
        this.id = id;
        this.shipmentId = shipmentId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getShipmentId() { return shipmentId; }
    public void setShipmentId(Long shipmentId) { this.shipmentId = shipmentId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }
}