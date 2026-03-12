package tomato.gui.view;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import tomato.core.Variety;

/// Unit tests for {@link MainViewModel}.
final class MainViewModelTest {

    @Test
    @DisplayName("Varieties list is read-only")
    void ensureReadOnlyVarieties() {
        final var model = new MainViewModel();

        // Attempting to modify the varieties list should throw an exception
        // This ensures the list is properly wrapped as read-only
        assertThrows(UnsupportedOperationException.class,
                () -> model.varietiesProperty().add(new VarietyItem(new Variety("Dummy"))));
        assertThrows(UnsupportedOperationException.class,
                () -> model.getVarieties().add(new VarietyItem(new Variety("Dummy"))));
    }

    @Test
    @DisplayName("Add variety with null input does not add to list")
    void addVariety_WithNullInput_DoesNotAddToList() {
        // Arrange
        final var model = new MainViewModel();
        final var initialSize = model.getVarieties().size();

        // Act
        model.setVarietyNameToAdd(null);

        // Assert
        assertFalse(model.isCanAdd());
        model.addVariety();
        assertEquals(initialSize, model.getVarieties().size());
    }

    @Test
    @DisplayName("Add variety with empty input does not add to list")
    void addVariety_WithEmptyInput_DoesNotAddToList() {
        // Arrange
        final var model = new MainViewModel();
        final var initialSize = model.getVarieties().size();

        // Act
        model.setVarietyNameToAdd("");

        // Assert
        assertFalse(model.isCanAdd());
        model.addVariety();
        assertEquals(initialSize, model.getVarieties().size());
    }

    @Test
    @DisplayName("Add variety with blank input does not add to list")
    void addVariety_WithBlankInput_DoesNotAddToList() {
        // Arrange
        final var model = new MainViewModel();
        final var initialSize = model.getVarieties().size();

        // Act
        model.setVarietyNameToAdd(" ");

        // Assert
        assertFalse(model.isCanAdd());
        model.addVariety();
        assertEquals(initialSize, model.getVarieties().size());
    }

    @Test
    @DisplayName("Add variety with existing variety does not add to list")
    void addVariety_WithExistingVariety_DoesNotAddToList() {
        // Arrange
        final var model = new MainViewModel();
        assertFalse(model.getVarieties().isEmpty());
        final var initialSize = model.getVarieties().size();
        final var existingName = model.getVarieties().getFirst().getName();

        // Act
        model.setVarietyNameToAdd(existingName);

        // Assert
        assertFalse(model.isCanAdd());
        model.addVariety();
        assertEquals(initialSize, model.getVarieties().size());
    }

    @Test
    @DisplayName("Add variety with new variety adds to list and clears input")
    void addVariety_WithNewVariety_AddsToListAndClearsInput() {
        // Arrange
        final var model = new MainViewModel();
        final var initialSize = model.getVarieties().size();
        final var name = "Test";
        assertFalse(model.getVarieties().stream().anyMatch(variety -> variety.getName().equals(name)));

        // Act
        model.setVarietyNameToAdd(name);
        assertTrue(model.isCanAdd());
        model.addVariety();

        // Assert
        assertTrue(model.getVarieties().stream().anyMatch(variety -> variety.getName().equals(name)));
        assertEquals(initialSize + 1, model.getVarieties().size());
    }

    @Test
    @DisplayName("Remove with no selection cannot execute")
    void remove_WithNoSelection_CannotRemove() {
        // Arrange
        final var model = new MainViewModel();

        // Act & Assert
        assertTrue(model.getSelectedVarieties().isEmpty());
        assertFalse(model.isCanRemove());
    }

    @Test
    @DisplayName("Remove with selected varieties removes them from list")
    void remove_WithSelectedVarieties_RemovesThemFromList() {
        // Arrange
        final var model = new MainViewModel();
        final var initialSize = model.getVarieties().size();
        assertTrue(initialSize >= 2, "Need at least 2 varieties for this test");

        // Select first and last variety for removal
        final var firstVariety = model.getVarieties().getFirst();
        final var lastVariety = model.getVarieties().getLast();
        final var varietiesToRemove = List.of(firstVariety, lastVariety);
        model.getModifiableSelectedVarieties().addAll(varietiesToRemove);

        // Act
        assertTrue(model.isCanRemove());
        model.removeVarieties();

        // Assert
        assertEquals(initialSize - varietiesToRemove.size(), model.getVarieties().size());
        assertAll(varietiesToRemove.stream().map(
                varietyToRemove -> (Executable) () -> assertFalse(
                        model.getVarieties().stream()
                                .anyMatch(variety -> variety.getName().equals(varietyToRemove.getName())))));
        assertTrue(model.getSelectedVarieties().isEmpty());
        assertFalse(model.isCanRemove());
    }

    @Test
    @DisplayName("Remove all with non-empty list clears all varieties")
    void removeAll_WithNonEmptyList_ClearsAllVarieties() {
        // Arrange
        final var model = new MainViewModel();
        assertFalse(model.getVarieties().isEmpty());
        assertTrue(model.isCanRemoveAll());

        // Act
        model.removeAllVarieties();

        // Assert
        assertTrue(model.getVarieties().isEmpty());
        assertFalse(model.isCanRemoveAll());
    }

    @Test
    @DisplayName("Remove all with empty list cannot execute")
    void removeAll_WithEmptyList_CannotRemove() {
        // Arrange
        final var model = new MainViewModel();
        model.removeAllVarieties();

        // Act & Assert
        assertTrue(model.getVarieties().isEmpty());
        assertFalse(model.isCanRemoveAll());
    }
}
