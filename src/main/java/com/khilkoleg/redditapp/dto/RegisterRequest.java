package com.khilkoleg.redditapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Oleg Khilko
 */

@Data
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
}
