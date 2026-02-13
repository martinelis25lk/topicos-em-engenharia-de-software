package br.com.lasanhaspec.carservice.repository;

import br.com.lasanhaspec.carservice.domain.models.CommunitySetup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetupRepository extends JpaRepository<CommunitySetup, Long> {

    List<CommunitySetup> findByVehicleCatalogModelId(Long Id);
}
