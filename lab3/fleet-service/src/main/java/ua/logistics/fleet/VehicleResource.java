package ua.logistics.fleet;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ua.logistics.fleet.model.Vehicle;
import ua.logistics.fleet.repository.VehicleRepository;

import java.util.List;

@Path("/api/vehicles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VehicleResource {

    @Inject
    VehicleRepository repository;

    @GET
    public List<Vehicle> getAllVehicles() {
        return repository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getVehicle(@PathParam("id") Long id) {
        Vehicle vehicle = repository.findById(id);
        if (vehicle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(vehicle).build();
    }

    @POST
    @Transactional // Обов'язково для запису в БД
    public Response createVehicle(Vehicle vehicle) {
        // id генерується базою, тому якщо прийшов id, ми його ігноруємо або обнуляємо
        vehicle.setId(null);
        repository.persist(vehicle);
        return Response.status(Response.Status.CREATED).entity(vehicle).build();
    }
}