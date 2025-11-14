package ua.logistics.billing.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import ua.logistics.billing.dto.ShipmentDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/shipments")
@RegisterRestClient(configKey = "shipments-api")
public interface ShipmentsRestClient {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    ShipmentDto getShipment(@PathParam("id") Long id);
}