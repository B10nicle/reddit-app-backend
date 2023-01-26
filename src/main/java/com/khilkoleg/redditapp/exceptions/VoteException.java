package com.khilkoleg.redditapp.exceptions;

/**
 * @author Oleg Khilko
 */

public class VoteException extends RuntimeException {
    public VoteException(String message) {
        super(message);
    }
}
