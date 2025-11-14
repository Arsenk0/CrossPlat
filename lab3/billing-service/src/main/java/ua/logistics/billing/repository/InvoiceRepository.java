package ua.logistics.billing.repository;

import ua.logistics.billing.model.Invoice;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class InvoiceRepository {
    private final Map<Long, Invoice> invoices = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @PostConstruct
    public void init() {
        create(new Invoice(null, 1L, 2500.0, "UAH", "PENDING"));
        create(new Invoice(null, 2L, 1800.0, "UAH", "PAID"));
    }

    public Invoice create(Invoice invoice) {
        Long id = idGenerator.getAndIncrement();
        invoice.setId(id);
        invoices.put(id, invoice);
        return invoice;
    }

    public Optional<Invoice> findById(Long id) {
        return Optional.ofNullable(invoices.get(id));
    }

    public Optional<Invoice> findByShipmentId(Long shipmentId) {
        return invoices.values().stream()
                .filter(i -> i.getShipmentId().equals(shipmentId))
                .findFirst();
    }

    public List<Invoice> findAll() {
        return new ArrayList<>(invoices.values());
    }

    public void update(Invoice invoice) {
        invoices.put(invoice.getId(), invoice);
    }
}
