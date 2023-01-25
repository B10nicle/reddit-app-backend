package com.khilkoleg.redditapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.khilkoleg.redditapp.model.Comment;

/**
 * @author Oleg Khilko
 */

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}