package com.khilkoleg.redditapp.mapper;

import com.khilkoleg.redditapp.dto.CommentDto;
import com.khilkoleg.redditapp.model.Comment;
import com.khilkoleg.redditapp.model.Post;
import com.khilkoleg.redditapp.model.User;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

/**
 * @author Oleg Khilko
 */

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "commentId", ignore = true)
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment map(CommentDto commentDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentDto mapToDto(Comment comment);
}