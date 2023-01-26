package com.khilkoleg.redditapp.service;

import com.khilkoleg.redditapp.exceptions.SubredditNotFoundException;
import com.khilkoleg.redditapp.exceptions.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import com.khilkoleg.redditapp.exceptions.PostNotFoundException;
import com.khilkoleg.redditapp.repository.SubredditRepository;
import com.khilkoleg.redditapp.repository.UserRepository;
import com.khilkoleg.redditapp.repository.PostRepository;
import com.khilkoleg.redditapp.mapper.PostMapper;
import com.khilkoleg.redditapp.dto.PostResponse;
import com.khilkoleg.redditapp.dto.PostRequest;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Oleg Khilko
 */


@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    @Transactional
    public void save(PostRequest postRequest) {
        var subreddit = subredditRepository.findBySubredditName(postRequest.getSubredditName()).orElseThrow(
                () -> new SubredditNotFoundException(postRequest.getSubredditName() + " is not found"));
        postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
    }

    public PostResponse getPost(Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post #" + id + " is not found"));
        return postMapper.mapToDto(post);
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        var subreddit = subredditRepository.findById(subredditId).orElseThrow(
                () -> new SubredditNotFoundException("Subreddit #" + subredditId + " is not found"));
        var posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    public List<PostResponse> getPostsByUsername(String username) {
        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User with name " + username + " cannot be found"));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
