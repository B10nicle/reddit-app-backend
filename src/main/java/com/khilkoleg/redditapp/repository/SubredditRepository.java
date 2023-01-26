package com.khilkoleg.redditapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.khilkoleg.redditapp.model.Subreddit;

import java.util.Optional;

/**
 * @author Oleg Khilko
 */

@Repository
public interface SubredditRepository extends JpaRepository <Subreddit, Long> {
    Optional<Subreddit> findBySubredditName(String subredditName);
}
