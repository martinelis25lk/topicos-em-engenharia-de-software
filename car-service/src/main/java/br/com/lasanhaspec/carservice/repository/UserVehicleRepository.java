package br.com.lasanhaspec.carservice.repository;

import br.com.lasanhaspec.carservice.domain.models.UserVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface UserVehicleRepository extends JpaRepository<UserVehicle, Long> {

    Optional <UserVehicle> findById(Long id);

    @Query("""
    SELECT DISTINCT uv
    FROM UserVehicle uv
    JOIN FETCH uv.vehicleCatalogModel
    JOIN uv.images img
    WHERE uv.active = true
""")
    List<UserVehicle> findFeedVehiclesWithImages();


    List<UserVehicle> findByUserId(Long id);
}
