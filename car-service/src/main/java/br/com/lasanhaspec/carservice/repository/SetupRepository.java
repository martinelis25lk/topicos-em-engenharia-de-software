package br.com.lasanhaspec.carservice.repository;

import br.com.lasanhaspec.carservice.domain.models.SetupModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SetupRepository extends JpaRepository<SetupModel, Long> {

    List<SetupModel> findByVehicleCatalogModelId(Long Id);
}
