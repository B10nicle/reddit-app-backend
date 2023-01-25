package com.khilkoleg.redditapp.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import com.khilkoleg.redditapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.Collection;

import static java.util.Collections.singletonList;

/**
 * @author Oleg Khilko
 */

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        var userOptional = userRepository.findByUsername(username);
        var user = userOptional.orElseThrow(
                () -> new UsernameNotFoundException("No user was found with username: " + username));

        return new User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getAuthorities());
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        return singletonList(new SimpleGrantedAuthority("USER"));
    }
}
