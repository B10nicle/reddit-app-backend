package com.khilkoleg.redditapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.khilkoleg.redditapp.model.Post;

/**
 * @author Oleg Khilko
 */

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
