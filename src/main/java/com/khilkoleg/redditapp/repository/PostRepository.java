package com.khilkoleg.redditapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.khilkoleg.redditapp.model.Subreddit;
import com.khilkoleg.redditapp.model.User;
import com.khilkoleg.redditapp.model.Post;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
