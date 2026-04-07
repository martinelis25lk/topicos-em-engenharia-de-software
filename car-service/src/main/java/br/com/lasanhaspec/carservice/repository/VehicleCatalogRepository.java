package br.com.lasanhaspec.carservice.repository;

import br.com.lasanhaspec.carservice.domain.enums.IssueStatus;
import br.com.lasanhaspec.carservice.domain.models.VehicleCatalogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface VehicleCatalogRepository extends JpaRepository<VehicleCatalogModel, Long> {


    @Query("SELECT DISTINCT v FROM VehicleCatalogModel v " +
            "JOIN Chronic_Issues c ON c.vehicleCatalogModel = v " +
            "WHERE c.status = :status")
    List<VehicleCatalogModel> findModelsWithIssuesByStatus(
            @Param("status") IssueStatus status);
}
