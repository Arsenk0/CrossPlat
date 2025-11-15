
package ua.logistics.gateway;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.*;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GatewayResource {

    // Для початку просто проксимо запити
    // Потім додамо REST клієнти

    @GET
    @Path("/vehicles")
    public List<Map<String, Object>> getVehicles() {
        // Тимчасово повертаємо хардкод
        List<Map<String, Object>> vehicles = new ArrayList<>();
        Map<String, Object> v = new HashMap<>();
        v.put("id", 1L);
        v.put("type", "TRUCK");
        v.put("message", "Gateway working! Connect REST clients next");
        vehicles.add(v);
        return vehicles;
    }

    @GET
    @Path("/shipments")
    public List<Map<String, Object>> getShipments() {
        List<Map<String, Object>> shipments = new ArrayList<>();
        Map<String, Object> s = new HashMap<>();
        s.put("id", 1L);
        s.put("status", "PENDING");
        s.put("message", "Gateway working! Connect REST clients next");
        shipments.add(s);
        return shipments;
    }
}