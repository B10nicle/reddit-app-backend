package com.khilkoleg.redditapp.exceptions;

/**
 * @author Oleg Khilko
 */

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
