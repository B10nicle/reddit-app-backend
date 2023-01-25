package com.khilkoleg.redditapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.khilkoleg.redditapp.model.Vote;

/**
 * @author Oleg Khilko
 */

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
