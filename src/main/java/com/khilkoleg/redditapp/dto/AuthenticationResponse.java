package com.khilkoleg.redditapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Oleg Khilko
 */

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String authenticationToken;
    private String username;
}
