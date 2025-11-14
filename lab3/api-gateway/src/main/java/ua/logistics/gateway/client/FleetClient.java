package ua.logistics.gateway.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/vehicles")
@RegisterRestClient(configKey="fleet-api")
public interface FleetClient {
    @GET
    String getAllVehicles();

    @GET
    @Path("/{id}")
    String getVehicle(Long id);

    @GET
    @Path("/drivers")
    String getAllDrivers();
}