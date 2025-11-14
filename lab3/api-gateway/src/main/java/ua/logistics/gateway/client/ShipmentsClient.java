package ua.logistics.gateway.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/shipments")
@RegisterRestClient(configKey="shipments-api")
public interface ShipmentsClient {
    @GET
    String getAllShipments();

    // Додати методи, які потрібні GatewayResource
    // Наприклад:
    @GET
    @Path("/{id}")
    String getShipment(Long id);

    @GET
    @Path("/create/{name}")
    String createShipment(String name);

    @GET
    @Path("/assign/{id}")
    String assignVehicle(Long id);
}