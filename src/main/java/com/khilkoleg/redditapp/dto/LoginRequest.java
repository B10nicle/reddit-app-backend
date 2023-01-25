package com.khilkoleg.redditapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Oleg Khilko
 */

@Data
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
