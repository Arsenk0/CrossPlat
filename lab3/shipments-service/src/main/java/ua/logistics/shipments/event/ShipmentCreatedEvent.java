package ua.logistics.shipments.event;

public class ShipmentCreatedEvent {
    public Long id;
    public String origin;
    public String destination;
    public String status;

    public ShipmentCreatedEvent() {}

    public ShipmentCreatedEvent(Long id, String origin, String destination, String status) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.status = status;
    }
}