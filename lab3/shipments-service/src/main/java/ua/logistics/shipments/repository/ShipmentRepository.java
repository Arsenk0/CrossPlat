package ua.logistics.shipments.repository;

import ua.logistics.shipments.model.Shipment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class ShipmentRepository {
    private final Map<Long, Shipment> shipments = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @PostConstruct
    public void init() {
        create(new Shipment(null, "Київ", "Львів", 10.0, "PENDING"));
        create(new Shipment(null, "Одеса", "Харків", 5.0, "ASSIGNED"));
        create(new Shipment(null, "Дніпро", "Запоріжжя", 15.0, "IN_TRANSIT"));
    }

    public Shipment create(Shipment shipment) {
        Long id = idGenerator.getAndIncrement();
        shipment.setId(id);
        shipments.put(id, shipment);
        return shipment;
    }

    public Optional<Shipment> findById(Long id) {
        return Optional.ofNullable(shipments.get(id));
    }

    public List<Shipment> findAll() {
        return new ArrayList<>(shipments.values());
    }

    public List<Shipment> findByStatus(String status) {
        return shipments.values().stream()
                .filter(s -> s.getStatus().equals(status))
                .toList();
    }

    public void update(Shipment shipment) {
        shipments.put(shipment.getId(), shipment);
    }

    public void delete(Long id) {
        shipments.remove(id);
    }
}