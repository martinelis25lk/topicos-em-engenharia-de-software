package br.com.lasanhaspec.carservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import br.com.lasanhaspec.carservice.domain.enums.IssueStatus;
import br.com.lasanhaspec.carservice.domain.enums.VoteType;
import br.com.lasanhaspec.carservice.domain.models.ChronicIssue;
import br.com.lasanhaspec.carservice.domain.models.IssueVote;
import br.com.lasanhaspec.carservice.domain.models.User;
import br.com.lasanhaspec.carservice.exception.BusinessException;
import br.com.lasanhaspec.carservice.repository.ChronicIssueRepository;
import br.com.lasanhaspec.carservice.repository.IssueVoteRepository;
import br.com.lasanhaspec.carservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class IssueVoteServiceTest {

    @Mock
    private IssueVoteRepository issueVoteRepository;

    @Mock
    private ChronicIssueRepository chronicIssueRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IssueVoteService issueVoteService;





    @Test
    void shouldCreateUsefulVoteWhenUserHasNotVotedYet() {
        Long issueId = 1L;
        String email = "user@test.com";

        ChronicIssue issue = new ChronicIssue();
        issue.setUsefulVotes(0);
        issue.setNotUsefulVotes(0);
        issue.setStatus(IssueStatus.PENDING);

        User user = new User();
        user.setId(10L);
        user.setEmail(email);

        when(chronicIssueRepository.findById(issueId))
                .thenReturn(Optional.of(issue));

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(issueVoteRepository.findByIssueIdAndUserId(issueId, 10L))
                .thenReturn(Optional.empty());

        issueVoteService.vote(issueId, email, VoteType.USEFUL);

        assertEquals(1, issue.getUsefulVotes());
        assertEquals(0, issue.getNotUsefulVotes());


        verify(issueVoteRepository).save(ArgumentMatchers.any(IssueVote.class));
        verify(chronicIssueRepository).save(issue);
    }



    @Test
    void shouldThrowWhenUserVotesSameWayTwice() {
        Long issueId = 1L;
        String email = "user@test.com";

        ChronicIssue issue = new ChronicIssue();
        issue.setUsefulVotes(1);
        issue.setNotUsefulVotes(0);
        issue.setStatus(IssueStatus.PENDING);

        User user = new User();
        user.setId(2L);
        user.setEmail(email);

        IssueVote existingVote = new IssueVote();
        existingVote.setUserId(2L);
        existingVote.setVoteType(VoteType.USEFUL);

        when(chronicIssueRepository.findById(issueId))
                .thenReturn(Optional.of(issue));

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(issueVoteRepository.findByIssueIdAndUserId(issueId, 2L))
                .thenReturn(Optional.of(existingVote));

        assertThrows(
                BusinessException.class,
                () -> issueVoteService.vote(issueId, email, VoteType.USEFUL)
        );
    }




    @Test
    void shouldChangeVoteFromUsefulToNotUseful() {
        Long issueId = 1L;
        String email = "user@test.com";

        ChronicIssue issue = new ChronicIssue();
        issue.setUsefulVotes(1);
        issue.setNotUsefulVotes(0);
        issue.setStatus(IssueStatus.PENDING);

        User user = new User();
        user.setId(2L);
        user.setEmail(email);

        IssueVote existingVote = new IssueVote();
        existingVote.setUserId(2L);
        existingVote.setVoteType(VoteType.USEFUL);

        when(chronicIssueRepository.findById(issueId))
                .thenReturn(Optional.of(issue));

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(issueVoteRepository.findByIssueIdAndUserId(issueId, 2L))
                .thenReturn(Optional.of(existingVote));

        issueVoteService.vote(issueId, email, VoteType.NOT_USEFUL);

        assertEquals(0, issue.getUsefulVotes());
        assertEquals(1, issue.getNotUsefulVotes());
        assertEquals(VoteType.NOT_USEFUL, existingVote.getVoteType());

        verify(issueVoteRepository).save(existingVote);
        verify(chronicIssueRepository).save(issue);
    }

} 
