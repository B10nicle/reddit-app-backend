package com.khilkoleg.redditapp.security;

import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.time.Instant;

/**
 * @author Oleg Khilko
 */

@Service
public class JwtProvider {
    private final JwtEncoder jwtEncoder;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @Autowired
    public JwtProvider(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication authentication) {
        var principal = (User) authentication.getPrincipal();
        return generateTokenWithUsername(authentication, principal.getUsername());
    }

    public String generateTokenWithUsername(Authentication authentication, String username) {
        var now = Instant.now();
        var scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusMillis(jwtExpirationInMillis))
                .subject(username)
                .claim("scope", scope)
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
