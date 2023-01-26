package com.khilkoleg.redditapp.mapper;

import com.khilkoleg.redditapp.dto.PostResponse;
import com.khilkoleg.redditapp.dto.PostRequest;
import com.khilkoleg.redditapp.model.Subreddit;
import com.khilkoleg.redditapp.model.Post;
import com.khilkoleg.redditapp.model.User;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

/**
 * @author Oleg Khilko
 */

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "postId", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.subredditName")
    @Mapping(target = "userName", source = "user.username")
    PostResponse mapToDto(Post post);
}
