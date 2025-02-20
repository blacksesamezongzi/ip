package duke.ui;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Performs interactions with the user.
 */
public class Ui {
    /**
     * Displays the welcome message.
     */
    public String showWelcome() {
        return "Ah, a new challenger enters the maze! \nI'm here to help you map out your journey and keep your goals in sight. \nPlease tell me, what are your plans today?";
    }

    public String getByeMessage() {
        return "Farewell, adventurer! May your path be clear and your tasks conquered. Until our next quest!";
    }
}