package com.khilkoleg.redditapp.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.annotation.Nullable;
import org.hibernate.Hibernate;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.FetchType.LAZY;

/**
 * @author Oleg Khilko
 */

@Getter
@Setter
@Entity
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long postId;
    @NotBlank(message = "Post name cannot be empty or null")
    private String postName;
    @Nullable
    private String url;
    @Lob
    @Nullable
    private String description;
    private Integer voteCount;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @ToString.Exclude
    private User user;
    private Instant createdDate;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    @ToString.Exclude
    private Subreddit subreddit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return postId != null && Objects.equals(postId, post.postId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
