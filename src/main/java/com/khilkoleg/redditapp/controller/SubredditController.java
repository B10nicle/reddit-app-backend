package com.khilkoleg.redditapp.controller;

import com.khilkoleg.redditapp.service.SubredditService;
import org.springframework.web.bind.annotation.*;
import com.khilkoleg.redditapp.dto.SubredditDto;
import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Oleg Khilko
 */

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/subreddit")
public class SubredditController {
    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
        return ResponseEntity
                .status(CREATED)
                .body(subredditService.save(subredditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
        return ResponseEntity
                .status(OK)
                .body(subredditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id) {
        return ResponseEntity
                .status(OK)
                .body(subredditService.getSubreddit(id));
    }
}
