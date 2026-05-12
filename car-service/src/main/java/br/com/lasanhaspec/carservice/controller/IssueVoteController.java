package br.com.lasanhaspec.carservice.controller;

import br.com.lasanhaspec.carservice.dto.IssueVoteRequestDTO;
import br.com.lasanhaspec.carservice.service.IssueVoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chronic-issues")
public class IssueVoteController {

    private final IssueVoteService issueVoteService;

    public IssueVoteController(IssueVoteService issueVoteService) {
        this.issueVoteService = issueVoteService;
    }

    @PostMapping("/{issueId}/vote")
    public ResponseEntity<Void> vote(
            @PathVariable Long issueId,
            @RequestBody IssueVoteRequestDTO dto
    ) {
        issueVoteService.vote(issueId, dto.getUserId(), dto.getVoteType());
        return ResponseEntity.ok().build();
    }
}