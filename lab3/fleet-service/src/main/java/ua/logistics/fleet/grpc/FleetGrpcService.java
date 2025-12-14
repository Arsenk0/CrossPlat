package ua.logistics.fleet.grpc;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import ua.logistics.fleet.model.Vehicle;
import ua.logistics.fleet.repository.VehicleRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Optional;

@GrpcService
public class FleetGrpcService implements FleetService {

    @Inject
    VehicleRepository vehicleRepository;

    @Override
    @Transactional
    public Uni<VehicleAvailabilityResponse> checkVehicleAvailability(VehicleAvailabilityRequest request) {
        return Uni.createFrom().item(() -> {
            // Тут використовуємо наш кастомний метод, він вже повертає Optional
            Optional<Vehicle> vehicle = vehicleRepository.findAvailableByCapacity(request.getMinCapacity());

            if (vehicle.isPresent()) {
                Vehicle v = vehicle.get();
                return VehicleAvailabilityResponse.newBuilder()
                        .setAvailable(true)
                        .setVehicleId(v.getId())
                        .setVehicleType(v.getType())
                        .setCapacity(v.getCapacity())
                        .build();
            } else {
                return VehicleAvailabilityResponse.newBuilder()
                        .setAvailable(false)
                        .build();
            }
        });
    }

    @Override
    @Transactional
    public Uni<ReserveVehicleResponse> reserveVehicle(ReserveVehicleRequest request) {
        return Uni.createFrom().item(() -> {
            // ВИПРАВЛЕННЯ: використовуємо findByIdOptional замість findById
            var vehicleOpt = vehicleRepository.findByIdOptional(request.getVehicleId());

            if (vehicleOpt.isEmpty()) {
                return ReserveVehicleResponse.newBuilder()
                        .setSuccess(false)
                        .setMessage("Vehicle not found")
                        .build();
            }

            Vehicle vehicle = vehicleOpt.get();

            if (!vehicle.isAvailable()) {
                return ReserveVehicleResponse.newBuilder()
                        .setSuccess(false)
                        .setMessage("Vehicle is not available")
                        .build();
            }

            // Змінюємо статус (Hibernate автоматично збереже зміни)
            vehicle.setAvailable(false);

            return ReserveVehicleResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Vehicle reserved successfully")
                    .setVehicleId(vehicle.getId())
                    .build();
        });
    }
}