package com.khilkoleg.redditapp.mapper;

import com.khilkoleg.redditapp.model.User;
import org.mapstruct.InheritInverseConfiguration;
import com.khilkoleg.redditapp.dto.SubredditDto;
import com.khilkoleg.redditapp.model.Subreddit;
import com.khilkoleg.redditapp.model.Post;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    Subreddit map(SubredditDto subredditDto, User user);

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }
}
