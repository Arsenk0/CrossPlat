package ua.logistics.fleet.grpc;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import ua.logistics.fleet.model.Vehicle;
import ua.logistics.fleet.repository.VehicleRepository;
import jakarta.inject.Inject;

@GrpcService
public class FleetGrpcService implements FleetService {

    @Inject
    VehicleRepository vehicleRepository;

    @Override
    public Uni<VehicleAvailabilityResponse> checkVehicleAvailability(VehicleAvailabilityRequest request) {
        return Uni.createFrom().item(() -> {
            var vehicle = vehicleRepository.findAvailableByCapacity(request.getMinCapacity());

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
    public Uni<ReserveVehicleResponse> reserveVehicle(ReserveVehicleRequest request) {
        return Uni.createFrom().item(() -> {
            var vehicleOpt = vehicleRepository.findById(request.getVehicleId());

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

            vehicle.setAvailable(false);
            vehicleRepository.update(vehicle);

            return ReserveVehicleResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Vehicle reserved successfully")
                    .setVehicleId(vehicle.getId())
                    .build();
        });
    }
}