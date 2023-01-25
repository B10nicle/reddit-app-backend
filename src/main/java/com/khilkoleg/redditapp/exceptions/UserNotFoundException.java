package com.khilkoleg.redditapp.exceptions;

/**
 * @author Oleg Khilko
 */

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
