package br.com.lasanhaspec.carservice.repository;

import br.com.lasanhaspec.carservice.domain.models.UserVehicle;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface UserVehicleRepository extends JpaRepository<UserVehicle, Long> {

    Optional <UserVehicle> findById(Long id);
}
