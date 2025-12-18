package ua.logistics.shipments;

import ua.logistics.shipments.event.ShipmentCreatedEvent; // Додано імпорт події
import ua.logistics.shipments.model.Shipment;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel; // Додано для Messaging
import org.eclipse.microprofile.reactive.messaging.Emitter; // Додано для Messaging

import java.net.URI;
import java.util.List;

@Path("/api/shipments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShipmentResource {

    // --- Додано: Емітер для відправки повідомлень у Kafka ---
    @Channel("shipments-out")
    Emitter<ShipmentCreatedEvent> shipmentEmitter;
    // --------------------------------------------------------

    @GET
    public List<Shipment> getAllShipments() {
        // Active Record метод для отримання всіх записів
        return Shipment.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getShipment(@PathParam("id") Long id) {
        Shipment shipment = Shipment.findById(id);
        if (shipment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(shipment).build();
    }

    @POST
    @Transactional // Обов'язково для запису в БД
    public Response createShipment(Shipment shipment) {
        // Встановлюємо час створення, якщо не передано
        if (shipment.createdAt == null) {
            shipment.createdAt = java.time.LocalDateTime.now();
        }
        // Зберігаємо в БД
        shipment.persist();

        // --- Додано: Створення та відправка події в Kafka ---
        ShipmentCreatedEvent event = new ShipmentCreatedEvent(
                shipment.id,
                shipment.origin,
                shipment.destination,
                shipment.status
        );
        shipmentEmitter.send(event);
        // ----------------------------------------------------

        return Response.created(URI.create("/api/shipments/" + shipment.id)).entity(shipment).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateShipment(@PathParam("id") Long id, Shipment updatedData) {
        Shipment entity = Shipment.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Оновлюємо поля
        entity.origin = updatedData.origin;
        entity.destination = updatedData.destination;
        entity.weight = updatedData.weight;
        entity.status = updatedData.status;
        entity.assignedVehicleId = updatedData.assignedVehicleId;
        entity.deliveredAt = updatedData.deliveredAt;

        // persist() викликати не обов'язково, об'єкт і так в managed стані всередині транзакції

        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteShipment(@PathParam("id") Long id) {
        boolean deleted = Shipment.deleteById(id);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}