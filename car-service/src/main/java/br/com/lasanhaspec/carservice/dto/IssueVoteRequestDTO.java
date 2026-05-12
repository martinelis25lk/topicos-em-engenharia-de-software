package br.com.lasanhaspec.carservice.dto;

import br.com.lasanhaspec.carservice.domain.enums.VoteType;

public class IssueVoteRequestDTO {

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    private Long userId;
    private VoteType voteType;


}
