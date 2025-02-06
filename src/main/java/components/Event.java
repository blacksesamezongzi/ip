package components;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDateTime.parse(from);
        this.to = LocalDateTime.parse(to);
    }

    public Event(String description, String from, String to, boolean isDone) {
        super(description, isDone);
        this.from = LocalDateTime.parse(from);
        this.to = LocalDateTime.parse(to);
    }

    public String getFrom() {
        return from.format(DateTimeFormatter.ofPattern("MMM d yyyy HH:mm"));
    }

    public String getTo() {
        return to.format(DateTimeFormatter.ofPattern("MMM d yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}