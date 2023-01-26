package com.khilkoleg.redditapp.exceptions;

/**
 * @author Oleg Khilko
 */

public class SubredditNotFoundException extends RuntimeException {
    public SubredditNotFoundException(String message) {
        super(message);
    }
}
