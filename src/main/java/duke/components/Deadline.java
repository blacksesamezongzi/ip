package duke.components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task with a specific due date and time.
 */
public class Deadline extends Task {
    protected String by;
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");

    /**
     * Constructs a Deadline object with the specified description and due date.
     *
     * @param description The description of the deadline task.
     * @param by The due date and time in the format "d/M/yyyy HHmm".
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Constructs a Deadline object with the specified description, due date, and completion status.
     *
     * @param description The description of the deadline task.
     * @param by The due date and time in the format "d/M/yyyy HHmm".
     * @param isDone The completion status of the task.
     */
    public Deadline(String description, String by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    /**
     * Returns the due date and time of the deadline task.
     *
     * @return The due date and time as a string.
     */
    public String getBy() {
        return by;
    }

    /**
     * Returns the formatted due date and time for printing.
     *
     * @return The formatted due date and time as a string in the format "MMM d yyyy HH:mm".
     */
    public String getByPrint() {
        LocalDateTime byDateTime = LocalDateTime.parse(by, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
        return byDateTime.format(OUTPUT_FORMATTER);
    }

    /**
     * Returns the string representation of the deadline task.
     *
     * @return The string representation of the deadline task, including its type, description, and due date.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getByPrint() + ")";
    }

    /**
     * Returns the input formatter for parsing due date and time.
     *
     * @return The DateTimeFormatter for parsing due date and time in the format "d/M/yyyy HHmm".
     */
    public static DateTimeFormatter getInputFormatter() {
        return DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    }
}