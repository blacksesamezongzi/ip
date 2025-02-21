package duke.components;

import duke.exceptions.*;

/**
 * Parses user commands.
 */
public class Parser {
    /**
     * Parses the given full command string and splits it into the command word and its arguments.
     *
     * @param fullCommand The full command string to be parsed.
     * @return A string array where the first element is the command word and the second element is the command arguments.
     * @throws UnknownCommandException If the command word is not recognized.
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