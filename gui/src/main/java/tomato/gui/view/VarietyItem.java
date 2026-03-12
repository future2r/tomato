package tomato.gui.view;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tomato.core.Variety;

/// Item shown in the varieties table.
public final class VarietyItem {

    private final StringProperty name = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> createdAt = new SimpleObjectProperty<>();

    /// Creates a new item from a core variety record.
    ///
    /// @param variety the core variety
    public VarietyItem(Variety variety) {
        requireNonNull(variety);

        this.name.set(variety.name());
        this.createdAt.set(variety.createdAt());
    }

    /// {@return the name property}
    public StringProperty nameProperty() {
        return this.name;
    }

    /// {@return the variety name}
    public String getName() {
        return nameProperty().get();
    }

    /// {@return the created-at property}
    public ObjectProperty<LocalDateTime> createdAtProperty() {
        return this.createdAt;
    }

    /// {@return the creation time}
    public LocalDateTime getCreatedAt() {
        return createdAtProperty().get();
    }
}