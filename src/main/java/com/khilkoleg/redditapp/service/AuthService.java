package com.khilkoleg.redditapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import com.khilkoleg.redditapp.repository.VerificationTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.khilkoleg.redditapp.exceptions.TokenNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import com.khilkoleg.redditapp.exceptions.UserNotFoundException;
import com.khilkoleg.redditapp.dto.AuthenticationResponse;
import com.khilkoleg.redditapp.repository.UserRepository;
import com.khilkoleg.redditapp.model.NotificationEmail;
import com.khilkoleg.redditapp.model.VerificationToken;
import com.khilkoleg.redditapp.security.JwtProvider;
import org.springframework.security.oauth2.jwt.Jwt;
import com.khilkoleg.redditapp.dto.RegisterRequest;
import com.khilkoleg.redditapp.dto.LoginRequest;
import org.springframework.stereotype.Service;
import com.khilkoleg.redditapp.model.User;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.UUID;

import static java.time.Instant.*;

/**
 * @author Oleg Khilko
 */

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final MailService mailService;

    @Transactional
    public void signUp(RegisterRequest registerRequest) {
        var user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(now());
        user.setEnabled(false);
        userRepository.save(user);

        var token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail(
                "Please confirm your email address",
                user.getEmail(),
                "Hi " + user.getUsername() + "," +
                        "\nClick here to confirm your email address and activate your account:" +
                        "\nhttp://localhost:8080/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user) {
        var token = UUID.randomUUID().toString();

        var verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryAt(now().plusMillis(50)); //TODO: поправить на авторизацию только в лимите времени
        verificationTokenRepository.save(verificationToken);

        return token;
    }

    @Transactional
    public void verifyAccount(String token) {
        var verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(
                () -> new TokenNotFoundException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        var username = verificationToken.getUser().getUsername();
        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User with name " + username + " cannot be found"));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        var token = jwtProvider.generateToken(authentication);
        return new AuthenticationResponse(token, loginRequest.getUsername());
    }

    public User getCurrentUser() {
        var principal = (Jwt) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return userRepository.findByUsername(principal.getSubject()).orElseThrow(
                () -> new UsernameNotFoundException("Username not found - " + principal.getSubject()));
    }
}
