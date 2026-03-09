package br.com.lasanhaspec.carservice.repository;


import br.com.lasanhaspec.carservice.domain.models.IssueVote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IssueVoteRepository  extends JpaRepository<IssueVote, Long> {


    Optional<IssueVote> findByIssueIdAndUserId(Long issueId, Long userId);


}
