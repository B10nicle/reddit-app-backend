package com.khilkoleg.redditapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.khilkoleg.redditapp.model.User;

import java.util.Optional;

/**
 * @author Oleg Khilko
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
