package com.khilkoleg.redditapp.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import org.hibernate.Hibernate;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * @author Oleg Khilko
 */

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userId;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @Email
    @NotEmpty(message = "Email is required")
    private String email;
    private Instant created;
    private boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return userId != null && Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
