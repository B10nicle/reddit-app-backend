package com.khilkoleg.redditapp.controller;

import com.khilkoleg.redditapp.service.PostService;
import org.springframework.web.bind.annotation.*;
import com.khilkoleg.redditapp.dto.PostResponse;
import com.khilkoleg.redditapp.dto.PostRequest;
import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Oleg Khilko
 */


@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity
                .status(OK)
                .body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity
                .status(OK)
                .body(postService.getPost(id));
    }

    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long id) {
        return ResponseEntity.
                status(OK)
                .body(postService.getPostsBySubreddit(id));
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username) {
        return ResponseEntity.
                status(OK)
                .body(postService.getPostsByUsername(username));
    }
}
