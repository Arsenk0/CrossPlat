package ua.logistics.ui.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import ua.logistics.ui.model.Shipment;

import java.util.List;

// configKey пов'язує цей інтерфейс з налаштуваннями в application.properties
@RegisterRestClient(configKey = "gateway-api")
@Path("/api")
public interface GatewayClient {

    @GET
    @Path("/shipments")
    List<Shipment> getShipments();
}
