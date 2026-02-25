package br.com.lasanhaspec.carservice.repository;

import br.com.lasanhaspec.carservice.domain.models.VehicleImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleImageRepository extends JpaRepository<VehicleImage, Long> {

    List<VehicleImage> findByUserVehicleImage(Long vehicleId);

    Long countByUserVehicleId(Long vehicleId);
}
