package br.com.lasanhaspec.carservice.repository;

import br.com.lasanhaspec.carservice.domain.models.VehicleImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleImageRepository extends JpaRepository<VehicleImage, Long> {

    List<VehicleImage> findByUserVehicleId(Long vehicleId);

    Optional<VehicleImage> findByIdAndUserVehicleId(Long imageId, Long vehicleId);

    Long countByUserVehicleId(Long vehicleId);
}
