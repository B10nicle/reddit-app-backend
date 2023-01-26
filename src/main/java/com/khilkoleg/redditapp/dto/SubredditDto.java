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
public class SubredditDto {
    private Long subredditId;
    private String subredditName;
    private String description;
    private Integer numberOfPosts;
}
