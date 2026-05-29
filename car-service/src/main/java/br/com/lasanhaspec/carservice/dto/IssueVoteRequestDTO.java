package br.com.lasanhaspec.carservice.dto;

import br.com.lasanhaspec.carservice.domain.enums.VoteType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class IssueVoteRequestDTO {


    private Long userId;

    @NotNull
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
