package ua.logistics.billing.service;

import ua.logistics.billing.model.Invoice;
import ua.logistics.billing.repository.InvoiceRepository;
import ua.logistics.billing.client.ShipmentsRestClient;
import ua.logistics.billing.dto.ShipmentDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDateTime;

@ApplicationScoped
public class BillingService {

    @Inject
    InvoiceRepository invoiceRepository;

    @Inject
    @RestClient
    ShipmentsRestClient shipmentsClient;

    private static final double BASE_RATE_PER_TON = 250.0; // UAH за тонну

    public Invoice createInvoiceForShipment(Long shipmentId) {
        // REST виклик до Shipments Service
        ShipmentDto shipment = shipmentsClient.getShipment(shipmentId);

        // Розрахунок суми
        double amount = calculateAmount(shipment.getWeight());

        Invoice invoice = new Invoice();
        invoice.setShipmentId(shipmentId);
        invoice.setAmount(amount);
        invoice.setCurrency("UAH");
        invoice.setStatus("PENDING");

        return invoiceRepository.create(invoice);
    }

    public Invoice markAsPaid(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        invoice.setStatus("PAID");
        invoice.setPaidAt(LocalDateTime.now());
        invoiceRepository.update(invoice);

        return invoice;
    }

    private double calculateAmount(double weight) {
        return weight * BASE_RATE_PER_TON;
    }
}