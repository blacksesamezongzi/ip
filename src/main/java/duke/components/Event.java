package duke.components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task with a description, start time, and end time.
 * Inherits from the Task class.
 */
public class Event extends Task {
    protected String from;
    protected String to;
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");

    /**
     * Constructs an Event with the specified description, start time, and end time.
     *
     * @param description The description of the event.
     * @param from The start time of the event in the format "d/M/yyyy HHmm".
     * @param to The end time of the event in the format "d/M/yyyy HHmm".
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Constructs an Event with the specified description, start time, end time, and completion status.
     *
     * @param description The description of the event.
     * @param from The start time of the event in the format "d/M/yyyy HHmm".
     * @param to The end time of the event in the format "d/M/yyyy HHmm".
     * @param isDone The completion status of the event.
     */
    public Event(String description, String from, String to, boolean isDone) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start time of the event.
     *
     * @return The start time of the event.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the end time of the event.
     *
     * @return The end time of the event.
     */
    public String getTo() {
        return to;
    }

    /**
     * Returns the formatted start time of the event for printing.
     *
     * @return The formatted start time of the event.
     */
    public String getFromPrint() {
        LocalDateTime fromDateTime = LocalDateTime.parse(from, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
        return fromDateTime.format(OUTPUT_FORMATTER);
    }

    /**
     * Returns the formatted end time of the event for printing.
     *
     * @return The formatted end time of the event.
     */
    public String getToPrint() {
        LocalDateTime toDateTime = LocalDateTime.parse(to, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
        return toDateTime.format(OUTPUT_FORMATTER);
    }

    /**
     * Returns a string representation of the event, including its type, description, and time range.
     *
     * @return A string representation of the event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + getFromPrint() + " to: " + getToPrint() + ")";
    }

    /**
     * Returns the input formatter for parsing date and time strings.
     *
     * @return The input formatter.
     */
    public static DateTimeFormatter getInputFormatter() {
        return DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    }
}