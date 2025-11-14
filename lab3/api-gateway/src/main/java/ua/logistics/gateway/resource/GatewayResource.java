package ua.logistics.gateway.resource;

import ua.logistics.gateway.client.ShipmentsClient;
import ua.logistics.gateway.client.FleetClient;
import ua.logistics.gateway.client.BillingClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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

    // Shipments endpoints
    @GET
    @Path("/shipments")
    public String getAllShipments() {
        return shipmentsClient.getAllShipments();
    }

    @GET
    @Path("/shipments/{id}")
    public String getShipment(@PathParam("id") Long id) {
        return shipmentsClient.getShipment(id);
    }

    @POST
    @Path("/shipments")
    public String createShipment(String shipment) {
        return shipmentsClient.createShipment(shipment);
    }

    @PUT
    @Path("/shipments/{id}/assign-vehicle")
    public String assignVehicle(@PathParam("id") Long id) {
        return shipmentsClient.assignVehicle(id);
    }

    // Fleet endpoints
    @GET
    @Path("/vehicles")
    public String getAllVehicles() {
        return fleetClient.getAllVehicles();
    }

    @GET
    @Path("/vehicles/{id}")
    public String getVehicle(@PathParam("id") Long id) {
        return fleetClient.getVehicle(id);
    }

    @GET
    @Path("/drivers")
    public String getAllDrivers() {
        return fleetClient.getAllDrivers();
    }

    // Billing endpoints
    @GET
    @Path("/invoices")
    public String getAllInvoices() {
        return billingClient.getAllInvoices();
    }

    @GET
    @Path("/invoices/{id}")
    public String getInvoice(@PathParam("id") Long id) {
        return billingClient.getInvoice(id);
    }

    @POST
    @Path("/invoices/create-for-shipment/{shipmentId}")
    public String createInvoice(@PathParam("shipmentId") Long shipmentId) {
        return billingClient.createInvoice(shipmentId);
    }

    // Агрегований endpoint - повна інформація про доставку
    @GET
    @Path("/delivery-info/{shipmentId}")
    public String getDeliveryInfo(@PathParam("shipmentId") Long shipmentId) {
        try {
            String shipment = shipmentsClient.getShipment(shipmentId);
            String invoice = billingClient.getInvoice(shipmentId);

            return String.format(
                    "{\"shipment\": %s, \"invoice\": %s}",
                    shipment,
                    invoice
            );
        } catch (Exception e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }
}