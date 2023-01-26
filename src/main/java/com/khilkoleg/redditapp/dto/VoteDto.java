package com.khilkoleg.redditapp.dto;

import com.khilkoleg.redditapp.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * @author Oleg Khilko
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {
    private VoteType voteType;
    private Long postId;
}