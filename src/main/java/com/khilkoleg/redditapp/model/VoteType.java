package com.khilkoleg.redditapp.model;

import com.khilkoleg.redditapp.exceptions.VoteNotFoundException;

import java.util.Arrays;

/**
 * @author Oleg Khilko
 */

public enum VoteType {
    UPVOTE(1),
    DOWNVOTE(-1);

    private int direction;

    VoteType(int direction) {
    }

    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new VoteNotFoundException("Vote not found"));
    }

    public Integer getDirection() {
        return direction;
    }
}
