package com.khilkoleg.redditapp.exceptions;

/**
 * @author Oleg Khilko
 */

public class VoteNotFoundException extends RuntimeException {
    public VoteNotFoundException(String message) {
        super(message);
    }
}
