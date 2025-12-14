package ua.logistics.fleet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String registrationNumber;
    private String type; // TRUCK, VAN, CARGO
    private double capacity; // тонн
    private boolean available;
    private Long currentDriverId;

    public Vehicle() {}

    public Vehicle(Long id, String registrationNumber, String type, double capacity, boolean available) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.type = type;
        this.capacity = capacity;
        this.available = available;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getCapacity() { return capacity; }
    public void setCapacity(double capacity) { this.capacity = capacity; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public Long getCurrentDriverId() { return currentDriverId; }
    public void setCurrentDriverId(Long currentDriverId) { this.currentDriverId = currentDriverId; }
}