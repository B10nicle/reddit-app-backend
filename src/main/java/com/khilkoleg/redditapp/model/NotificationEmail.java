package com.khilkoleg.redditapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Oleg Khilko
 */

@Data
@AllArgsConstructor
public class NotificationEmail {
    private String subject;
    private String recipient;
    private String body;
}
