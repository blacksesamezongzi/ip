package duke.components;

import duke.exceptions.*;

/**
 * Parses user commands.
 */
public class Parser {
    /**
     * Parses the full command entered by the user.
     *
     * @param fullCommand The full command entered by the user.
     * @return An array containing the command word and command arguments.
     * @throws UnknownCommandException If the command is unknown.
     */
    public static String[] parse(String fullCommand) throws UnknownCommandException {
        String[] commandParts = fullCommand.split(" ", 2);
        String commandWord = commandParts[0];
        String commandArgs = commandParts.length > 1 ? commandParts[1] : "";

        switch (commandWord) {
        case "bye":
        case "list":
        case "mark":
        case "unmark":
        case "todo":
        case "deadline":
        case "event":
        case "delete":
        case "find":
        case "tag":
        case "untag":
            return new String[] { commandWord, commandArgs };
        default:
            throw new UnknownCommandException();
        }
    }
}