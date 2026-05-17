package br.com.lasanhaspec.carservice.dto;

import br.com.lasanhaspec.carservice.domain.enums.VoteType;

public class IssueVoteRequestDTO {


    private Long userId;
    private VoteType voteType;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }



    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public VoteType getVoteType() {
        return voteType;
    }


}
