package tomato.gui;

import java.net.URL;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tomato.gui.view.MainView;

/// Entry point of the Tomato application.
public final class TomatoApplication extends Application {

    /// Launches the application.
    ///
    /// @param args the command-line arguments
    public static void main(String[] args) {
        launch(args);
    }

    /// Starts the application by loading the main view, applying the theme, setting
    /// application icons, and showing the primary stage.
    @Override
    public void start(final Stage primaryStage) throws Exception {
        final var root = MainView.load();

        final var scene = new Scene(root);
        scene.getStylesheets().add(MainView.class.getResource("theme.css").toExternalForm());

        primaryStage.setScene(scene);

        primaryStage.setTitle("Tomato");

        primaryStage.getIcons().addAll(
                IntStream.of(16, 32, 48, 64, 128, 256)
                        .mapToObj(size -> "/tomato/gui/image/tomato_%d.png".formatted(size))
                        .map(getClass()::getResource)
                        .map(URL::toString)
                        .map(Image::new)
                        .toList());

        primaryStage.show();
    }
}
