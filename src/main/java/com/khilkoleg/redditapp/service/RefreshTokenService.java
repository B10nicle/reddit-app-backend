package com.khilkoleg.redditapp.service;

import com.khilkoleg.redditapp.exceptions.TokenNotFoundException;
import com.khilkoleg.redditapp.repository.RefreshTokenRepository;
import org.springframework.transaction.annotation.Transactional;
import com.khilkoleg.redditapp.model.RefreshToken;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import static java.time.Instant.*;
import static java.util.UUID.*;

/**
 * @author Oleg Khilko
 */

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public RefreshToken generateRefreshToken() {
        var refreshToken = new RefreshToken();
        refreshToken.setToken(randomUUID().toString());
        refreshToken.setCreatedDate(now());

        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token).orElseThrow(
                () -> new TokenNotFoundException("Invalid refresh token"));
    }

    @Transactional
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}