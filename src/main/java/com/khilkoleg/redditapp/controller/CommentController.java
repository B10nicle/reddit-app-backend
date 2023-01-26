package com.khilkoleg.redditapp.controller;

import com.khilkoleg.redditapp.service.CommentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.khilkoleg.redditapp.dto.CommentDto;
import lombok.AllArgsConstructor;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Oleg Khilko
 */

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        return ResponseEntity
                .status(CREATED)
                .body(commentService.save(commentDto));
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity
                .status(OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForUser(@PathVariable String userName) {
        return ResponseEntity
                .status(OK)
                .body(commentService.getAllCommentsForUser(userName));
    }
}