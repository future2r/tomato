package tomato.gui.view;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/// Implements the controller for the main view.
public final class MainViewController {

    @FXML
    private MainViewModel viewModel;

    @FXML
    private TextField varietyNameToAddTextField;
    @FXML
    private TableView<VarietyItem> varietyTableView;

    /// Creates a new instance of the main view controller.
    public MainViewController() {
        // A public no-arg constructor is required for the FXML loader
        // Implicit generation risks API instability when introducing more constructors
    }

    /// Called after the FXML fields have been injected.
    @FXML
    private void initialize() {
        // Bind the text field to the view model
        this.varietyNameToAddTextField.textProperty().bindBidirectional(viewModel.varietyNameToAddProperty());

        // Allow multi-selection in the table view
        this.varietyTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Bind the selected varieties to the view model
        Bindings.bindContent(viewModel.getModifiableSelectedVarieties(),
                this.varietyTableView.getSelectionModel().getSelectedItems());

        // Initialize the controls after the view is shown
        Platform.runLater(this::initializeControls);
    }

    /// Initializes the controls after the view is shown.
    private void initializeControls() {
        // Set the initial focus
        this.varietyTableView.requestFocus();

        // Select the first item
        this.varietyTableView.getSelectionModel().selectFirst();
    }

    /// Event handler for the add button.
    @FXML
    private void add() {
        // Let the model add the item
        this.viewModel.addVariety();
    }

    /// Event handler for the remove button.
    @FXML
    private void remove() {
        // Get the current selection and clear it
        final var selectedIndex = this.varietyTableView.getSelectionModel().getSelectedIndex();

        // Let the model remove the selected items
        this.viewModel.removeVarieties();

        // Restore the selection if possible
        this.varietyTableView.getSelectionModel().clearSelection();
        if (!this.varietyTableView.getItems().isEmpty() && selectedIndex >= 0) {
            final var newSelectedIndex = Math.min(selectedIndex, this.varietyTableView.getItems().size() - 1);
            this.varietyTableView.getSelectionModel().clearAndSelect(newSelectedIndex);
        }
    }

    /// Event handler for the remove all button.
    @FXML
    private void removeAll() {
        // Clear the selection
        this.varietyTableView.getSelectionModel().clearSelection();

        // Let the model remove all items
        this.viewModel.removeAllVarieties();
    }

    /// Event handler for key presses on the variety table view. Delegates to
    /// {@link #remove()} when the Delete key is pressed.
    @FXML
    private void varietyTableViewKeyPressed(final KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            event.consume();
            remove();
        }
    }
}
