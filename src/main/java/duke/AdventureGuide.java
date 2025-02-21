package duke;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import duke.ui.AdventureGuideBot;
import java.io.IOException;
import duke.ui.MainWindow;

/**
 * The main class to run the AdventureGuide application.
 */
public class AdventureGuide extends Application {

    private AdventureGuideBot bot = new AdventureGuideBot();

    /**
     * Starts the AdventureGuide application.
     * 
     * @param stage The stage to display the application.
     * @throws IOException If an input or output exception occurred.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AdventureGuide.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setAdventureGuideBot(bot);
            stage.setTitle("Adventure Guide");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method to start the AdventureGuide application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}