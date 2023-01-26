package com.khilkoleg.redditapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.khilkoleg.redditapp.model.Vote;
import com.khilkoleg.redditapp.model.Post;
import com.khilkoleg.redditapp.model.User;

import java.util.Optional;

/**
 * @author Oleg Khilko
 */

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
