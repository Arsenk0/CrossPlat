package ua.logistics.billing.service;

import ua.logistics.billing.event.ShipmentCreatedEvent;
import ua.logistics.billing.model.Invoice;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import java.time.LocalDateTime;

@ApplicationScoped
public class InvoiceGenerator {

    @Incoming("shipments-in") // Вказуємо назву каналу
    @Transactional
    public void processShipmentEvent(ShipmentCreatedEvent event) {
        System.out.println("Received shipment event for ID: " + event.id);

        // Перевірка, чи не існує вже рахунку для цього вантажу (щоб уникнути дублікатів)
        // (У реальному житті тут потрібна складніша логіка, але для лаби ок)

        // Створюємо новий рахунок
        Invoice invoice = new Invoice();
        invoice.shipmentId = event.id;
        invoice.createdAt = LocalDateTime.now();
        invoice.currency = "UAH";
        invoice.status = "PENDING";

        // Проста логіка розрахунку ціни (наприклад, фіксована або залежно від довжини назви міста :))
        invoice.amount = 500.00;

        // Зберігаємо в БД (Active Record)
        invoice.persist();

        System.out.println("Invoice created for Shipment ID: " + event.id + " with amount: " + invoice.amount);
    }
}