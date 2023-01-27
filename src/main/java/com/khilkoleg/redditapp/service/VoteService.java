package com.khilkoleg.redditapp.service;

import com.khilkoleg.redditapp.exceptions.PostNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import com.khilkoleg.redditapp.repository.PostRepository;
import com.khilkoleg.redditapp.repository.VoteRepository;
import com.khilkoleg.redditapp.exceptions.VoteException;
import com.khilkoleg.redditapp.mapper.VoteMapper;
import org.springframework.stereotype.Service;
import com.khilkoleg.redditapp.dto.VoteDto;
import lombok.AllArgsConstructor;

import static com.khilkoleg.redditapp.model.VoteType.UPVOTE;

/**
 * @author Oleg Khilko
 */

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final VoteMapper voteMapper;

    @Transactional
    public VoteDto vote(VoteDto voteDto) {
        var post = postRepository.findById(voteDto.getPostId()).orElseThrow(
                () -> new PostNotFoundException("Post #" + voteDto.getPostId() + " is not found"));
        var voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new VoteException("You have already "
                    + voteDto.getVoteType() + "D for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(voteMapper.map(voteDto, post, authService.getCurrentUser()));
        postRepository.save(post);

        return voteDto;
    }
}