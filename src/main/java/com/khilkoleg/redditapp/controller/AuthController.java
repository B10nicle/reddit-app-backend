package com.khilkoleg.redditapp.controller;

import com.khilkoleg.redditapp.service.RefreshTokenService;
import com.khilkoleg.redditapp.dto.AuthenticationResponse;
import com.khilkoleg.redditapp.dto.RefreshTokenRequest;
import com.khilkoleg.redditapp.dto.RegisterRequest;
import com.khilkoleg.redditapp.service.AuthService;
import org.springframework.web.bind.annotation.*;
import com.khilkoleg.redditapp.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import jakarta.validation.Valid;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Oleg Khilko
 */

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final RefreshTokenService refreshTokenService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest) {
        authService.signUp(registerRequest);
        return new ResponseEntity<>("User was successfully registered", CREATED);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account was successfully activated", OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity
                .status(OK)
                .body("Refresh token was successfully deleted");
    }
}
