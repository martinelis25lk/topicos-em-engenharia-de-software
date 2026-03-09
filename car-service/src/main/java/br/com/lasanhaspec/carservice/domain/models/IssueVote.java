package br.com.lasanhaspec.carservice.domain.models;
import br.com.lasanhaspec.carservice.domain.enums.VoteType;


// entidade evento individual

//reppresenta a ação de um usuario sobre aquele problema cronico
// exemplo: usuario 55 marcou como useful

// ela guarda:[
// qual issue foi votada
// qual usuario votou
// que tipo de voto

// essa entidade representa uma única ação individual




import jakarta.persistence.*;

@Entity
@Table(name="issue_votes",
uniqueConstraints = @UniqueConstraint(columnNames = {"issue_id", "user_id"}))
public class IssueVote {


    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;



    @ManyToOne(optional = false)
    @JoinColumn(name = "issue_id")
    private ChronicIssue issue;



    @Column(nullable = false)
    private Long user_id;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoteType voteType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChronicIssue getIssue() {
        return issue;
    }

    public void setIssue(ChronicIssue issue) {
        this.issue = issue;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }





}
