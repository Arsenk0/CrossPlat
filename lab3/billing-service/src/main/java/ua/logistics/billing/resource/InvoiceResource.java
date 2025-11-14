package ua.logistics.billing.resource;

import ua.logistics.billing.model.Invoice;
import ua.logistics.billing.repository.InvoiceRepository;
import ua.logistics.billing.service.BillingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/invoices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceResource {

    @Inject
    InvoiceRepository invoiceRepository;

    @Inject
    BillingService billingService;

    @GET
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getInvoice(@PathParam("id") Long id) {
        return invoiceRepository.findById(id)
                .map(invoice -> Response.ok(invoice).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/shipment/{shipmentId}")
    public Response getInvoiceByShipment(@PathParam("shipmentId") Long shipmentId) {
        return invoiceRepository.findByShipmentId(shipmentId)
                .map(invoice -> Response.ok(invoice).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Path("/create-for-shipment/{shipmentId}")
    public Response createInvoice(@PathParam("shipmentId") Long shipmentId) {
        try {
            Invoice invoice = billingService.createInvoiceForShipment(shipmentId);
            return Response.status(Response.Status.CREATED).entity(invoice).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}/pay")
    public Response markAsPaid(@PathParam("id") Long id) {
        try {
            Invoice invoice = billingService.markAsPaid(id);
            return Response.ok(invoice).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }
}