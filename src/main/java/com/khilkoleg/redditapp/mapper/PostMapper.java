package com.khilkoleg.redditapp.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import com.khilkoleg.redditapp.repository.CommentRepository;
import com.khilkoleg.redditapp.repository.VoteRepository;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.khilkoleg.redditapp.service.AuthService;
import com.khilkoleg.redditapp.dto.PostResponse;
import com.khilkoleg.redditapp.dto.PostRequest;
import com.khilkoleg.redditapp.model.*;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

import static com.khilkoleg.redditapp.model.VoteType.DOWNVOTE;
import static com.khilkoleg.redditapp.model.VoteType.UPVOTE;

/**
 * @author Oleg Khilko
 */

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    @Mapping(target = "user", source = "user")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", expression = "java(postRequest.getDescription())")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "postId", source = "postId")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "subredditName", source = "subreddit.subredditName")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            var voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(
                            post, authService.getCurrentUser());
            return voteForPostByUser.filter(
                    vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}
