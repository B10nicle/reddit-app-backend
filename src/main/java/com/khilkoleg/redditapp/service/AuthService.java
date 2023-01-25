package com.khilkoleg.redditapp.service;

import com.khilkoleg.redditapp.dto.AuthenticationResponse;
import com.khilkoleg.redditapp.security.JwtProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import com.khilkoleg.redditapp.repository.VerificationTokenRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.khilkoleg.redditapp.exceptions.TokenNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import com.khilkoleg.redditapp.exceptions.UserNotFoundException;
import com.khilkoleg.redditapp.repository.UserRepository;
import com.khilkoleg.redditapp.model.NotificationEmail;
import com.khilkoleg.redditapp.model.VerificationToken;
import com.khilkoleg.redditapp.dto.RegisterRequest;
import com.khilkoleg.redditapp.dto.LoginRequest;
import org.springframework.stereotype.Service;
import com.khilkoleg.redditapp.model.User;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.UUID;

/**
 * @author Oleg Khilko
 */

@Service
@Transactional
@AllArgsConstructor
public class AuthService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final MailService mailService;

    public void signUp(RegisterRequest registerRequest) {
        var user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
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
        verificationTokenRepository.save(verificationToken);

        return token;
    }

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
}
