package br.com.lasanhaspec.carservice.repository;

import br.com.lasanhaspec.carservice.domain.models.IssueOcurrence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IssueOccurrenceRepository  extends JpaRepository<IssueOcurrence, Long> {

    List<IssueOcurrence> findByChronicIssueId(Long issueId);

    boolean existsByChronicIssueIdAndUserVehicleId(Long issueId, Long vehicleId);


}
