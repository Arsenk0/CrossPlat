package ua.logistics.gateway.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/api/shipments")
@RegisterRestClient(configKey="shipments-api")
public interface ShipmentsClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    String getAllShipments();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    String getShipment(@PathParam("id") Long id);

    // ВИПРАВЛЕНО: Був GET, став POST, щоб приймати JSON
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    String createShipment(Object shipmentData);

    @PUT
    @Path("/{id}/assign-vehicle")
    @Produces(MediaType.APPLICATION_JSON)
    String assignVehicle(@PathParam("id") Long id);
}