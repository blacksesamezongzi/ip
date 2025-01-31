package Exceptions;

public class UnknownCommandException extends AdventureGuideException {
    public UnknownCommandException() {
        super("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }
}