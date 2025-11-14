package ua.logistics.gateway.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/api/invoices")
@RegisterRestClient(configKey = "billing-api")
public interface BillingClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    String getAllInvoices();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    String getInvoice(@PathParam("id") Long id);

    @POST
    @Path("/create-for-shipment/{shipmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    String createInvoice(@PathParam("shipmentId") Long shipmentId);
}
