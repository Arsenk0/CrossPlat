package ua.logistics.fleet.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import ua.logistics.fleet.model.Vehicle;
import java.util.Optional;

@ApplicationScoped
public class VehicleRepository implements PanacheRepository<Vehicle> {

    // Методи persist(), listAll(), findById() вже є "під капотом".

    // Нам потрібно реалізувати лише специфічний пошук для gRPC
    public Optional<Vehicle> findAvailableByCapacity(double minCapacity) {
        // SQL аналог: SELECT * FROM vehicles WHERE available = true AND capacity >= minCapacity LIMIT 1
        return find("available = ?1 and capacity >= ?2", true, minCapacity)
                .firstResultOptional();
    }
}