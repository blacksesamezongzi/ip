package duke.ui;

import java.util.Scanner;

/**
 * Performs interactions with the user.
 */
public class Ui {
    private Scanner sc;

    /**
     * Constructs a Ui object and initializes the scanner.
     */
    public Ui() {
        sc = new Scanner(System.in);
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm adventureGuide");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a line separator.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(" " + message);
    }

    /**
     * Reads a command from the user.
     *
     * @return The command entered by the user.
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
    }

    /**
     * Displays the loading error message.
     */
    public void showLoadingError() {
        System.out.println(" OOPS!!! An error occurred while loading tasks from file.");
    }
}