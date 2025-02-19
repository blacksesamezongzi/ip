package duke.ui;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Performs interactions with the user.
 */
public class Ui {
    private VBox dialogContainer;
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/AdventureGuide.jpg"));
    /**
     * Constructs a Ui object and initializes the dialog container.
     */
    public Ui() {
        this.dialogContainer = new VBox();
    }

    /**
     * Displays the welcome message.
     */
    public String showWelcome() {
        return "Ah, a new challenger enters the maze! \nI'm here to help you map out your journey and keep your goals in sight. \nPlease tell me, what are your plans today?";
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        dialogContainer.getChildren().add(DialogBox.getDukeDialog("Bye. Hope to see you again soon!", dukeImage));
    }

    /**
     * Displays the loading error message.
     */
    public void showLoadingError() {
        dialogContainer.getChildren().add(DialogBox.getDukeDialog("OOPS!!! An error occurred while loading tasks from file.", dukeImage));
    }

    public String getByeMessage() {
        return "Farewell, adventurer! May your path be clear and your tasks conquered. Until our next quest!";
    }
}