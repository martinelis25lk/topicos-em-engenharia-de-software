package br.com.lasanhaspec.carservice.service;


import br.com.lasanhaspec.carservice.domain.enums.IssueStatus;
import br.com.lasanhaspec.carservice.domain.enums.VoteType;
import br.com.lasanhaspec.carservice.domain.models.ChronicIssue;
import br.com.lasanhaspec.carservice.domain.models.IssueVote;
import br.com.lasanhaspec.carservice.exception.ResourceNotFoundException;
import br.com.lasanhaspec.carservice.repository.ChronicIssueRepository;
import br.com.lasanhaspec.carservice.repository.IssueVoteRepository;
import br.com.lasanhaspec.carservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class IssueVoteService {

    private final IssueVoteRepository issueVoteRepository;
    private final ChronicIssueRepository chronicIssueRepository;
    private final UserRepository userRepository;



    public IssueVoteService(IssueVoteRepository issueVoteRepository,
                            ChronicIssueRepository chronicIssueRepository,
                            UserRepository userRepository){

        this.issueVoteRepository = issueVoteRepository;
        this.chronicIssueRepository = chronicIssueRepository;
        this.userRepository = userRepository;
    }



    @Transactional
    public void vote(Long issueId, Long userId, VoteType voteType) {
        ChronicIssue chronicIssue = chronicIssueRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found kkservice"));


        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        Optional<IssueVote> existingVote =
                issueVoteRepository.findByIssueIdAndUserId(issueId, userId);


        if ((existingVote.isEmpty())) {

            IssueVote newIssueVote = new IssueVote();
            newIssueVote.setIssue(chronicIssue);
            newIssueVote.setUserId(userId);
            newIssueVote.setVoteType(voteType);
            issueVoteRepository.save(newIssueVote);


            if (voteType == VoteType.USEFUL) {
                chronicIssue.setUsefulVotes(chronicIssue.getUsefulVotes() + 1);
            } else {
                chronicIssue.setNotUsefulVotes(chronicIssue.getNotUsefulVotes() + 1);
            }


        } else {

            IssueVote vote = existingVote.get();

            if (vote.getVoteType() == voteType) {
                return;
            }

            if (vote.getVoteType() == VoteType.USEFUL) {
                chronicIssue.setUsefulVotes(chronicIssue.getUsefulVotes() - 1);

            } else {
                chronicIssue.setNotUsefulVotes(chronicIssue.getNotUsefulVotes() - 1);
            }

            if (voteType == VoteType.USEFUL) {
                chronicIssue.setUsefulVotes(chronicIssue.getUsefulVotes() + 1);
            } else {
                chronicIssue.setNotUsefulVotes(chronicIssue.getNotUsefulVotes() + 1);
            }


            vote.setVoteType(voteType);

            issueVoteRepository.save(vote);
        }

        // se numero de votos da entidade cronicos for > 10,setar campo getusewfulvotes
        if(chronicIssue.getUsefulVotes() >= 10
                && chronicIssue.getStatus() == IssueStatus.PENDING) {
            chronicIssue.setStatus(IssueStatus.IN_REVIEW);
        }



        chronicIssueRepository.save(chronicIssue);


    }


}


// voto individual
// IssueVote
// -- issue
// -- userId
// -- voteType


//agregador estatistico
// ChronicIssue
//-- usefulVotes
// notUsefulVotes


//testar
// contador nao negativo
//troca de voto ajusta os dois lados
//voto dupplicado nao cria nova linha
// voto novo incrementa corretamente?
//remoção de voto decrementa


// voto novo
//troca de voto
//voto igual ao anterior
// usuario inexistente ou issue inexistente
