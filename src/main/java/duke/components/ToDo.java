package duke.components;

/**
 * Represents a ToDo task with a description.
 * Inherits from the Task class.
 */
public class ToDo extends Task {
    /**
     * Constructs a ToDo object with the specified description.
     *
     * @param description The description of the ToDo task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Constructs a ToDo object with the specified description and completion status.
     *
     * @param description The description of the ToDo task.
     * @param isDone The completion status of the task.
     */
    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}