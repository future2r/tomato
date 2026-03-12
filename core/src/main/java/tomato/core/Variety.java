package tomato.core;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

/// Immutable tomato variety record in the database layer.
/// 
/// @param name      the variety name
/// @param createdAt the creation time
public record Variety(String name, LocalDateTime createdAt) {

    /// Creates a new variety with the current time as the creation time.
    /// 
    /// @param name the variety name
    public Variety(final String name) {
        this(name, LocalDateTime.now());
    }

    /// Creates a new variety with the specified name and creation time.
    public Variety {
        requireNonNull(name);
        requireNonNull(createdAt);
    }
}