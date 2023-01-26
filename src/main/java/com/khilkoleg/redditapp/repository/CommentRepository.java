package com.khilkoleg.redditapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.khilkoleg.redditapp.model.Comment;
import com.khilkoleg.redditapp.model.Post;
import com.khilkoleg.redditapp.model.User;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}