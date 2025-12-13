package ua.logistics.gateway.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType; // Не забудьте цей імпорт!
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import ua.logistics.gateway.client.*;
import java.util.List;
import java.util.Collections;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON) // ВАЖЛИВО: Кажемо, що завжди віддаємо JSON
@Consumes(MediaType.APPLICATION_JSON) // ВАЖЛИВО: Кажемо, що приймаємо JSON
public class GatewayResource {

    @Inject
    @RestClient
    ShipmentsClient shipmentsClient;

    @Inject
    @RestClient
    FleetClient fleetClient;

    @Inject
    @RestClient
    BillingClient billingClient;

    // --- SHIPMENTS ---
    @GET
    @Path("/shipments")
    public Response getShipments() {
        try {
            return Response.ok(shipmentsClient.getAllShipments()).build();
        } catch (Exception e) {
            // Фейковий об'єкт при помилці
            var fallback = new ShipmentDto(999L, "System (Offline)", "Maintenance", "ERROR");
            return Response.ok(List.of(fallback)).build();
        }
    }

    @POST
    @Path("/shipments")
    public Response createShipment(Object shipment) {
        try {
            return Response.ok(shipmentsClient.createShipment(shipment)).build();
        } catch (Exception e) {
            // Повертаємо JSON з помилкою, а не просто текст
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(new ErrorDto("Service unavailable")).build();
        }
    }

    @PUT
    @Path("/shipments/{id}/assign-vehicle")
    public Response assignVehicle(@PathParam("id") Long id) {
        try {
            return Response.ok(shipmentsClient.assignVehicle(id)).build();
        } catch (Exception e) {
            return Response.serverError().entity(new ErrorDto("Failed to assign")).build();
        }
    }

    // --- FLEET ---
    @GET
    @Path("/vehicles")
    public Response getVehicles() {
        try {
            return Response.ok(fleetClient.getAllVehicles()).build();
        } catch (Exception e) {
            return Response.ok(Collections.emptyList()).build();
        }
    }

    // --- BILLING ---
    @POST
    @Path("/invoices/create-for-shipment/{id}")
    public Response createInvoice(@PathParam("id") Long id) {
        try {
            return Response.ok(billingClient.createInvoice(id)).build();
        } catch (Exception e) {
            return Response.serverError().entity(new ErrorDto("Billing failed")).build();
        }
    }

    // DTO класи
    public static class ShipmentDto {
        public Long id;
        public String origin;
        public String destination;
        public String status;

        public ShipmentDto(Long id, String origin, String destination, String status) {
            this.id = id;
            this.origin = origin;
            this.destination = destination;
            this.status = status;
        }
    }

    public static class ErrorDto {
        public String error;
        public ErrorDto(String error) { this.error = error; }
    }
}