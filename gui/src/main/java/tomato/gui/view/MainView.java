package tomato.gui.view;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/// Utility class for the main view.
public final class MainView {

    /// {@return a cell factory that formats a {@link LocalDateTime} according to
    /// the current locale}
    public static Callback<TableColumn<?, LocalDateTime>, TableCell<VarietyItem, LocalDateTime>> createLocalDateTimeCellFactory() {
        return column -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter
                    .ofLocalizedDateTime(FormatStyle.MEDIUM)
                    .withLocale(Locale.getDefault());

            @Override
            protected void updateItem(final LocalDateTime item, final boolean empty) {
                super.updateItem(item, empty);
                setText(item == null || empty ? null : formatter.format(item));
            }
        };
    }

    /// Loads the main view from the FXML file.
    /// 
    /// @return The root node of the view
    /// @throws IOException If the FXML file cannot be loaded
    public static Parent load() throws IOException {
        // Load the I18N resources
        final var resourceBundle = ResourceBundle.getBundle("tomato.gui.i18n.messages");

        // Configure the FXML loader
        final var fxmlLoader = new FXMLLoader(MainView.class.getResource("MainView.fxml"), resourceBundle);

        // Load and return the root node
        return fxmlLoader.load();
    }
}
