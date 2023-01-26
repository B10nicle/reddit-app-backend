package com.khilkoleg.redditapp.controller;

import com.khilkoleg.redditapp.dto.AuthenticationResponse;
import com.khilkoleg.redditapp.dto.RegisterRequest;
import com.khilkoleg.redditapp.service.AuthService;
import org.springframework.web.bind.annotation.*;
import com.khilkoleg.redditapp.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Oleg Khilko
 */

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
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
}
