package br.com.lasanhaspec.carservice.repository;


import br.com.lasanhaspec.carservice.domain.models.ChronicIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChronicIssueRepository extends JpaRepository<ChronicIssue, Long> {
}
