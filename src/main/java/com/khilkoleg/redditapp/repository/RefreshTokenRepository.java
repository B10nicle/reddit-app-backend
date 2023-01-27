package com.khilkoleg.redditapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.khilkoleg.redditapp.model.RefreshToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Oleg Khilko
 */

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
