package com.khilkoleg.redditapp.exceptions;

/**
 * @author Oleg Khilko
 */

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
