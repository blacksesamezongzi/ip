package Exceptions;

public class EmptyDescriptionException extends AdventureGuideException {
    public EmptyDescriptionException(String taskType) {
        super("OOPS!!! The description of a " + taskType + " cannot be empty.");
    }
}