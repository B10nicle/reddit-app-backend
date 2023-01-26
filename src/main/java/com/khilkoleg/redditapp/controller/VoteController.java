package com.khilkoleg.redditapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.khilkoleg.redditapp.service.VoteService;
import org.springframework.http.ResponseEntity;
import com.khilkoleg.redditapp.dto.VoteDto;

import lombok.AllArgsConstructor;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Oleg Khilko
 */

@RestController
@AllArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<VoteDto> vote(@RequestBody VoteDto voteDto) {
        return ResponseEntity
                .status(CREATED)
                .body(voteService.vote(voteDto));
    }
}
