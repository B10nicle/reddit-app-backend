package com.khilkoleg.redditapp.exceptions;

/**
 * @author Oleg Khilko
 */

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String message) {
        super(message);
    }
}
