package com.khilkoleg.redditapp.service;

import com.khilkoleg.redditapp.exceptions.PostNotFoundException;
import com.khilkoleg.redditapp.exceptions.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import com.khilkoleg.redditapp.repository.CommentRepository;
import com.khilkoleg.redditapp.repository.PostRepository;
import com.khilkoleg.redditapp.repository.UserRepository;
import com.khilkoleg.redditapp.model.NotificationEmail;
import com.khilkoleg.redditapp.mapper.CommentMapper;
import com.khilkoleg.redditapp.dto.CommentDto;
import org.springframework.stereotype.Service;
import com.khilkoleg.redditapp.model.User;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Oleg Khilko
 */

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private static final String POST_URL = "";
    private final MailContentBuilder mailContentBuilder;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final AuthService authService;
    private final MailService mailService;

    @Transactional
    public CommentDto save(CommentDto commentDto) {
        var post = postRepository.findById(commentDto.getPostId()).orElseThrow(
                () -> new PostNotFoundException("Post #" + commentDto.getCommentId() + " is not found"));
        commentRepository.save(commentMapper.map(commentDto, post, authService.getCurrentUser()));

        var message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());

        return commentDto;
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " commented on your post", user.getEmail(), message));
    }

    public List<CommentDto> getAllCommentsForPost(Long id) {
        var post = postRepository.findById(id).orElseThrow(
                () -> new PostNotFoundException("Post #" + id + " is not found"));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

    public List<CommentDto> getAllCommentsForUser(String username) {
        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User with name " + username + " cannot be found"));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }
}
