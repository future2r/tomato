module tomato.gui {

    requires transitive javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    requires tomato.core;
    requires javafx.base;

    exports tomato.gui;

    opens tomato.gui.view to javafx.base, javafx.fxml;

    opens tomato.gui.image;
}