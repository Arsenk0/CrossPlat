package ua.logistics.ui;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import ua.logistics.ui.client.GatewayClient;

@Path("/")
@Authenticated
public class LogisticsPage {

    @Inject
    Template home;

    @Inject
    @RestClient
    GatewayClient gatewayClient;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        // Отримуємо списки з Gateway
        var shipmentsList = gatewayClient.getShipments();
        var invoicesList = gatewayClient.getInvoices();

        // Передаємо обидва списки в шаблон
        return home.data("shipments", shipmentsList)
                .data("invoices", invoicesList);
    }
}