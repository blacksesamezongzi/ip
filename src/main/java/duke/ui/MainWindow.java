package duke.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.PauseTransition;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private AdventureGuideBot bot;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.jpg"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/AdventureGuide.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the AdventureGuideBot instance */
    public void setAdventureGuideBot(AdventureGuideBot bot) {
        this.bot = bot;
        showWelcomeMessage();
        handleLoadingError();
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = bot.getResponse(input);
        addDialogBoxes(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();

        if (input.equals("bye")) {
            handleBye();
        }
    }

    /**
     * Adds multiple dialog boxes to the dialog container.
     *
     * @param dialogBoxes The dialog boxes to add.
     */
    private void addDialogBoxes(DialogBox... dialogBoxes) {
        dialogContainer.getChildren().addAll(dialogBoxes);
    }

    private void showWelcomeMessage() {
        dialogContainer.getChildren().add(DialogBox.getDukeDialog(bot.getUi().showWelcome(), dukeImage));
    }

    private void handleBye() {
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> {
            Stage stage = (Stage) dialogContainer.getScene().getWindow();
            stage.close();
        });
        delay.play();
    }

    private void handleLoadingError() {
        if (bot.getLoadingError() != null) {
            dialogContainer.getChildren().add(DialogBox.getDukeDialog(bot.getLoadingError(), dukeImage));
        }
    }

    public VBox getDialogContainer() {
        return dialogContainer;
    }
}