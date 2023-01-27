package com.khilkoleg.redditapp.service;

import com.khilkoleg.redditapp.exceptions.SubredditNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import com.khilkoleg.redditapp.repository.SubredditRepository;
import com.khilkoleg.redditapp.mapper.SubredditMapper;
import com.khilkoleg.redditapp.dto.SubredditDto;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static java.util.stream.Collectors.*;

/**
 * @author Oleg Khilko
 */

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;
    private final AuthService authService;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        var save = subredditRepository.save(subredditMapper.map(subredditDto, authService.getCurrentUser()));
        subredditDto.setSubredditId(save.getSubredditId());
        return subredditDto;
    }

    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapToDto)
                .collect(toList());
    }

    public SubredditDto getSubreddit(Long id) {
        var subreddit = subredditRepository.findById(id).orElseThrow(
                () -> new SubredditNotFoundException("Subreddit with id #" + id + " is not found"));
        return subredditMapper.mapToDto(subreddit);
    }
}
