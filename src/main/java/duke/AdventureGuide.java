package duke;

import duke.ui.AdventureGuideBot;

/**
 * The main class to run the AdventureGuide application.
 */
public class AdventureGuide {
    /**
     * The main method to start the AdventureGuide application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        AdventureGuideBot bot = new AdventureGuideBot();
        bot.start();
    }
}