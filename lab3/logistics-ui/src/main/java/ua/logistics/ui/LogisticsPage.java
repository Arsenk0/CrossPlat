package ua.logistics.ui;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import ua.logistics.ui.client.GatewayClient;

@Path("/")
public class LogisticsPage {

    @Inject
    Template home; // Це посилання на home.html

    @Inject
    @RestClient
    GatewayClient gatewayClient;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        // 1. Отримуємо дані з Gateway
        var shipmentsList = gatewayClient.getShipments();

        // 2. Передаємо їх у шаблон під ключем "shipments"
        return home.data("shipments", shipmentsList);
    }
}