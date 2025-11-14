package ua.logistics.fleet.repository;

import ua.logistics.fleet.model.Vehicle;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class VehicleRepository {
    private final Map<Long, Vehicle> vehicles = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @PostConstruct
    public void init() {
        // Фейкові дані
        create(new Vehicle(null, "AA1234BB", "TRUCK", 20.0, true));
        create(new Vehicle(null, "AA5678CC", "VAN", 5.0, true));
        create(new Vehicle(null, "AA9012DD", "CARGO", 15.0, false));
        create(new Vehicle(null, "AA3456EE", "TRUCK", 18.0, true));
    }

    public Vehicle create(Vehicle vehicle) {
        Long id = idGenerator.getAndIncrement();
        vehicle.setId(id);
        vehicles.put(id, vehicle);
        return vehicle;
    }

    public Optional<Vehicle> findById(Long id) {
        return Optional.ofNullable(vehicles.get(id));
    }

    public List<Vehicle> findAll() {
        return new ArrayList<>(vehicles.values());
    }

    public List<Vehicle> findAvailable() {
        return vehicles.values().stream()
                .filter(Vehicle::isAvailable)
                .toList();
    }

    public Optional<Vehicle> findAvailableByCapacity(double minCapacity) {
        return vehicles.values().stream()
                .filter(v -> v.isAvailable() && v.getCapacity() >= minCapacity)
                .findFirst();
    }

    public void update(Vehicle vehicle) {
        vehicles.put(vehicle.getId(), vehicle);
    }
}