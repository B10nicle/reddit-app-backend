package com.khilkoleg.redditapp.model;

import jakarta.validation.constraints.NotEmpty;
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

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotEmpty
    private String text;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    @ToString.Exclude
    private Post post;
    private Instant createdDate;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @ToString.Exclude
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;
        return id != null && Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
