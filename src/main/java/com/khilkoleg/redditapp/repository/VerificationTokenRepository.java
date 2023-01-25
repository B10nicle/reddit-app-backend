package com.khilkoleg.redditapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.khilkoleg.redditapp.model.VerificationToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Oleg Khilko
 */

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
}
