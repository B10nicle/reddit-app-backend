package com.khilkoleg.redditapp.model;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.Hibernate;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Objects;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.FetchType.LAZY;

/**
 * @author Oleg Khilko
 */

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Subreddit {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long subredditId;
    @NotBlank(message = "Community name is required")
    private String subredditName;
    @NotBlank(message = "Description is required")
    private String description;
    @OneToMany(fetch = LAZY)
    @ToString.Exclude
    private List<Post> posts;
    private Instant createdDate;
    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Subreddit subreddit = (Subreddit) o;
        return subredditId != null && Objects.equals(subredditId, subreddit.subredditId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
