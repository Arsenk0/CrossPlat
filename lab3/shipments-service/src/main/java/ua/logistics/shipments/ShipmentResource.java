package ua.logistics.shipments;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.*;

@Path("/api/shipments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShipmentResource {

    @GET
    public List<Map<String, Object>> getAllShipments() {
        List<Map<String, Object>> shipments = new ArrayList<>();

        Map<String, Object> s1 = new HashMap<>();
        s1.put("id", 1L);
        s1.put("origin", "Київ");
        s1.put("destination", "Львів");
        s1.put("weight", 10.0);
        s1.put("status", "PENDING");
        shipments.add(s1);

        return shipments;
    }

    @GET
    @Path("/{id}")
    public Map<String, Object> getShipment(@PathParam("id") Long id) {
        Map<String, Object> shipment = new HashMap<>();
        shipment.put("id", id);
        shipment.put("origin", "Київ");
        shipment.put("destination", "Львів");
        shipment.put("weight", 10.0);
        shipment.put("status", "PENDING");
        return shipment;
    }
}