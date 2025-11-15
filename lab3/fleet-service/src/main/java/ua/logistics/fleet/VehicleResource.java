package ua.logistics.fleet;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.*;

@Path("/api/vehicles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VehicleResource {

    @GET
    public List<Map<String, Object>> getAllVehicles() {
        List<Map<String, Object>> vehicles = new ArrayList<>();

        Map<String, Object> v1 = new HashMap<>();
        v1.put("id", 1L);
        v1.put("registrationNumber", "AA1234BB");
        v1.put("type", "TRUCK");
        v1.put("capacity", 20.0);
        v1.put("available", true);
        vehicles.add(v1);

        Map<String, Object> v2 = new HashMap<>();
        v2.put("id", 2L);
        v2.put("registrationNumber", "AA5678CC");
        v2.put("type", "VAN");
        v2.put("capacity", 5.0);
        v2.put("available", true);
        vehicles.add(v2);

        return vehicles;
    }

    @GET
    @Path("/{id}")
    public Map<String, Object> getVehicle(@PathParam("id") Long id) {
        Map<String, Object> vehicle = new HashMap<>();
        vehicle.put("id", id);
        vehicle.put("registrationNumber", "AA1234BB");
        vehicle.put("type", "TRUCK");
        vehicle.put("capacity", 20.0);
        vehicle.put("available", true);
        return vehicle;
    }
}