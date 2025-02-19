package duke.exceptions;

public class InvalidDateFormatException extends AdventureGuideException {
    public InvalidDateFormatException() {
        super("OOPS!!! The date format is invalid. Please use the format 'dd/MM/yyyy HHmm'.");
    }
}
