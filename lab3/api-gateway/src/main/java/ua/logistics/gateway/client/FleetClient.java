package ua.logistics.gateway.client;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.List;

@RegisterRestClient(configKey = "fleet-api")
@Path("/api/vehicles")
public interface FleetClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Object> getAllVehicles();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Object getVehicle(@PathParam("id") Long id);

    @GET
    @Path("/drivers")
    @Produces(MediaType.APPLICATION_JSON)
    List<Object> getAllDrivers();
}