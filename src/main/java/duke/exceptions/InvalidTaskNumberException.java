package duke.exceptions;

public class InvalidTaskNumberException extends AdventureGuideException {
    public InvalidTaskNumberException() {
        super("OOPS!!! The task number is invalid.");
    }
}