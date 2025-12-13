package ua.logistics.gateway.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import ua.logistics.gateway.client.*;

@Path("/api")
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
        return Response.ok(shipmentsClient.getAllShipments()).build();
    }

    @POST
    @Path("/shipments")
    public Response createShipment(Object shipment) {
        return Response.ok(shipmentsClient.createShipment(shipment)).build();
    }

    @PUT
    @Path("/shipments/{id}/assign-vehicle")
    public Response assignVehicle(@PathParam("id") Long id) {
        return Response.ok(shipmentsClient.assignVehicle(id)).build();
    }

    // --- FLEET ---
    @GET
    @Path("/vehicles")
    public Response getVehicles() {
        return Response.ok(fleetClient.getAllVehicles()).build();
    }

    // --- BILLING ---
    @POST
    @Path("/invoices/create-for-shipment/{id}")
    public Response createInvoice(@PathParam("id") Long id) {
        return Response.ok(billingClient.createInvoice(id)).build();
    }
}