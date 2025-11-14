package ua.logistics.fleet.repository;

import ua.logistics.fleet.model.Driver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class DriverRepository {
    private final Map<Long, Driver> drivers = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @PostConstruct
    public void init() {
        create(new Driver(null, "Іван Петренко", "DL123456", true));
        create(new Driver(null, "Марія Коваль", "DL789012", true));
        create(new Driver(null, "Олег Сидоренко", "DL345678", false));
    }

    public Driver create(Driver driver) {
        Long id = idGenerator.getAndIncrement();
        driver.setId(id);
        drivers.put(id, driver);
        return driver;
    }

    public Optional<Driver> findById(Long id) {
        return Optional.ofNullable(drivers.get(id));
    }

    public List<Driver> findAll() {
        return new ArrayList<>(drivers.values());
    }

    public List<Driver> findAvailable() {
        return drivers.values().stream()
                .filter(Driver::isAvailable)
                .toList();
    }

    public void update(Driver driver) {
        drivers.put(driver.getId(), driver);
    }
}