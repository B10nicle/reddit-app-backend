package com.khilkoleg.redditapp.mapper;

import com.khilkoleg.redditapp.dto.VoteDto;
import com.khilkoleg.redditapp.model.User;
import com.khilkoleg.redditapp.model.Post;
import com.khilkoleg.redditapp.model.Vote;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

/**
 * @author Oleg Khilko
 */

@Mapper(componentModel = "spring")
public interface VoteMapper {
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "voteType", expression = "java(voteDto.getVoteType())")
    Vote map(VoteDto voteDto, Post post, User user);
}
