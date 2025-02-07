package duke.components;

import duke.exceptions.*;

public class Parser {
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
                return new String[] { commandWord, commandArgs };
            default:
                throw new UnknownCommandException();
        }
    }
}