package tomato.core;

import java.util.Collections;
import java.util.List;

/// Implements the database for the application.
public final class Database {

    private final List<Variety> varieties;

    /// Creates a new instance of the database with some initial varieties.
    public Database() {
        this.varieties = List.of(
                new Variety("Roma"),
                new Variety("Beefsteak"),
                new Variety("Cherry"),
                new Variety("San Marzano"),
                new Variety("Brandywine"));
    }

    /// {@return an unmodifiable list of all tomato varieties in the database}
    public List<Variety> getAll() {
        return Collections.unmodifiableList(this.varieties);
    }
}
