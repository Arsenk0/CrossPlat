package ua.logistics.billing.service;

import ua.logistics.billing.model.Invoice;
import ua.logistics.billing.client.ShipmentsRestClient;
import ua.logistics.billing.dto.ShipmentDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;

@ApplicationScoped
public class BillingService {

    // InvoiceRepository більше не потрібен

    @Inject
    @RestClient
    ShipmentsRestClient shipmentsClient;

    private static final double BASE_RATE_PER_TON = 250.0;

    @Transactional // Потрібно для запису в БД
    public Invoice createInvoiceForShipment(Long shipmentId) {
        // 1. Отримуємо дані про вантаж (цей код працюватиме, якщо запущено shipments-service)
        // Для тестування можна закоментувати виклик клієнта і задати fake вагу, якщо сервіс shipments недоступний
        ShipmentDto shipment = shipmentsClient.getShipment(shipmentId);
        double weight = shipment != null ? shipment.getWeight() : 10.0; // Фолбек на випадок помилки

        // 2. Рахуємо
        double amount = calculateAmount(weight);

        // 3. Створюємо і зберігаємо
        Invoice invoice = new Invoice();
        invoice.shipmentId = shipmentId;
        invoice.amount = amount;
        invoice.currency = "UAH";
        invoice.status = "PENDING";
        invoice.createdAt = LocalDateTime.now();

        invoice.persist(); // Active Record збереження
        return invoice;
    }

    @Transactional
    public Invoice markAsPaid(Long invoiceId) {
        // Active Record пошук
        Invoice invoice = Invoice.findById(invoiceId);

        if (invoice == null) {
            throw new RuntimeException("Invoice not found");
        }

        invoice.status = "PAID";
        invoice.paidAt = LocalDateTime.now();
        // invoice.persist() не обов'язково, бо об'єкт у транзакції, зміни підтягнуться автоматично

        return invoice;
    }

    private double calculateAmount(double weight) {
        return weight * BASE_RATE_PER_TON;
    }
}