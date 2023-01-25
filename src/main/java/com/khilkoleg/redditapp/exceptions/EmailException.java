package com.khilkoleg.redditapp.exceptions;

/**
 * @author Oleg Khilko
 */

public class EmailException extends RuntimeException {
    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
