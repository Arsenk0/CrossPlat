package ua.logistics.shipments.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Shipment extends PanacheEntity {

    // В Active Record поля зазвичай public
    // Поле 'id' вже є в класі PanacheEntity, тому ми його тут не пишемо

    public String origin;
    public String destination;
    public double weight; // тонн
    public String status; // PENDING, ASSIGNED, IN_TRANSIT, DELIVERED
    public Long assignedVehicleId;
    public LocalDateTime createdAt;
    public LocalDateTime deliveredAt;

    // Порожній конструктор обов'язковий для JPA
    public Shipment() {}

    // Зручний конструктор для створення нових об'єктів
    public Shipment(String origin, String destination, double weight, String status) {
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }
}