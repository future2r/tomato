package tomato.gui.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tomato.core.Database;
import tomato.core.Variety;

/// Implements the model for the main view.
public final class MainViewModel {

    private final Database database;

    private final StringProperty varietyNameToAdd = new SimpleStringProperty();

    private final ObservableList<VarietyItem> modifiableVarieties = FXCollections.observableArrayList();
    private final ReadOnlyListWrapper<VarietyItem> varieties = new ReadOnlyListWrapper<>(
            FXCollections.unmodifiableObservableList(modifiableVarieties));

    private final ObservableList<VarietyItem> modifiableSelectedVarieties = FXCollections.observableArrayList();
    private final ReadOnlyListWrapper<VarietyItem> selectedVarieties = new ReadOnlyListWrapper<>(
            modifiableSelectedVarieties);

    private final ReadOnlyStringWrapper operatingSystem = new ReadOnlyStringWrapper();
    private final ReadOnlyStringWrapper runtime = new ReadOnlyStringWrapper();

    private final ReadOnlyBooleanWrapper canAdd = new ReadOnlyBooleanWrapper();
    private final ReadOnlyBooleanWrapper canRemove = new ReadOnlyBooleanWrapper();
    private final ReadOnlyBooleanWrapper canRemoveAll = new ReadOnlyBooleanWrapper();

    /// Creates a new instance of the main view model.
    public MainViewModel() {
        this.database = new Database();

        this.database.getAll().stream()
                .map(VarietyItem::new)
                .forEach(this.modifiableVarieties::add);

        this.operatingSystem.set(createOperatingSystemName());
        this.runtime.set(createRuntimeName());

        // Add is allowed only if not empty and not already existing
        this.canAdd.bind(Bindings.createBooleanBinding(this::checkCanAdd, this.varietyNameToAdd, this.varieties));

        // Remove is allowed only if there are selected varieties
        this.canRemove
                .bind(Bindings.createBooleanBinding(() -> !getSelectedVarieties().isEmpty(), this.selectedVarieties));

        // Remove all is allowed only if the list is not empty
        this.canRemoveAll.bind(Bindings.createBooleanBinding(() -> !getVarieties().isEmpty(), this.varieties));
    }

    /// {@return the operating system name from system properties}
    private static String createOperatingSystemName() {
        return System.getProperty("os.name");
    }

    /// {@return the runtime name and version from system properties}
    private static String createRuntimeName() {
        return System.getProperty("java.runtime.name") + " " + System.getProperty("java.runtime.version");
    }

    /// {@return whether a variety can be added based on the current input and
    /// existing varieties}
    private boolean checkCanAdd() {
        final var name = getVarietyNameToAdd();
        if (name == null)
            return false;

        final var trimmedName = name.trim();
        return !trimmedName.isBlank()
                && getVarieties().stream().noneMatch(variety -> variety.getName().equals(trimmedName));
    }

    /// {@return the property for the variety to add}
    public StringProperty varietyNameToAddProperty() {
        return this.varietyNameToAdd;
    }

    /// {@return the variety to add}
    public String getVarietyNameToAdd() {
        return varietyNameToAddProperty().get();
    }

    /// Sets the variety to add.
    /// 
    /// @param variety the variety to add
    public void setVarietyNameToAdd(String variety) {
        varietyNameToAddProperty().set(variety);
    }

    /// {@return the property for the varieties}
    public ReadOnlyListProperty<VarietyItem> varietiesProperty() {
        return this.varieties.getReadOnlyProperty();
    }

    /// {@return the varieties}
    public ObservableList<VarietyItem> getVarieties() {
        return varietiesProperty().get();
    }

    /// {@return the property for the selected varieties}
    public ReadOnlyListProperty<VarietyItem> selectedVarietiesProperty() {
        return this.selectedVarieties.getReadOnlyProperty();
    }

    /// {@return the selected varieties}
    public ObservableList<VarietyItem> getSelectedVarieties() {
        return selectedVarietiesProperty().get();
    }

    /// {@return the modifiable selected varieties for binding from the controller}
    ObservableList<VarietyItem> getModifiableSelectedVarieties() {
        return this.modifiableSelectedVarieties;
    }

    /// {@return the property for the canAdd}
    public ReadOnlyBooleanProperty canAddProperty() {
        return this.canAdd.getReadOnlyProperty();
    }

    /// {@return whether a variety can be added}
    public boolean isCanAdd() {
        return canAddProperty().get();
    }

    /// {@return the property for whether varieties can be removed}
    public ReadOnlyBooleanProperty canRemoveProperty() {
        return this.canRemove.getReadOnlyProperty();
    }

    /// {@return whether varieties can be removed}
    public boolean isCanRemove() {
        return canRemoveProperty().get();
    }

    /// {@return the property for whether all varieties can be removed}
    public ReadOnlyBooleanProperty canRemoveAllProperty() {
        return this.canRemoveAll.getReadOnlyProperty();
    }

    /// {@return whether all varieties can be removed}
    public boolean isCanRemoveAll() {
        return canRemoveAllProperty().get();
    }

    /// Adds the variety to the list if allowed.
    void addVariety() {
        if (isCanAdd()) {
            final var trimmed = getVarietyNameToAdd().trim();
            this.modifiableVarieties.add(new VarietyItem(new Variety(trimmed)));
            setVarietyNameToAdd("");
        }
    }

    /// Removes the selected varieties from the list.
    void removeVarieties() {
        this.modifiableVarieties.removeAll(getSelectedVarieties());
        this.modifiableSelectedVarieties.clear();
    }

    /// Removes all varieties from the list.
    void removeAllVarieties() {
        this.modifiableVarieties.clear();
    }

    /// {@return the property for the operating system}
    public ReadOnlyStringProperty operatingSystemProperty() {
        return this.operatingSystem.getReadOnlyProperty();
    }

    /// {@return the operating system}
    public String getOperatingSystem() {
        return operatingSystemProperty().get();
    }

    /// {@return the property for the runtime}
    public ReadOnlyStringProperty runtimeProperty() {
        return this.runtime.getReadOnlyProperty();
    }

    /// {@return the runtime}
    public String getRuntime() {
        return runtimeProperty().get();
    }
}
