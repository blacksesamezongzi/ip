package duke.exceptions;

public class EmptyIndexException extends AdventureGuideException {
    public EmptyIndexException(String command) {
        super("OOPS!!! The index of a " + command + " cannot be empty.");
    }
}
