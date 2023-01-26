package com.khilkoleg.redditapp.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * @author Oleg Khilko
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long postId;
    private String postName;
    private String url;
    private String description;
    private String userName;
    private String subredditName;
    private Integer voteCount;
    private Integer commentCount;
    private String duration;
    private boolean upVote;
    private boolean downVote;
}
