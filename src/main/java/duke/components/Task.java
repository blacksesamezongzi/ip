package duke.components;

/**
 * Represents a task with a description, optional tag and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    protected String tag;

    /**
     * Constructs a Task object with the specified description.
     * The task is marked as not done by default.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Constructs a Task object with the specified description and completion status.
     * 
     * @param description The description of the task.
     * @param isDone The completion status of the task.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the completion status of the task.
     *
     * @return The completion status of the task.
     */
    public boolean isDone() {
        return isDone;
    }

    public void addTag(String tag) {
        this.tag = tag;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return The status icon of the task, "X" if the task is done, " " if the task is not done.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public String getTag() {
        return tag;
    }

    public boolean hasTag() {
        return tag != null;
    }

    /**
     * Set the tag of the task.
     * 
     * @param tag The tag to be set.
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description + (tag == null ? "" : " #" + tag);
    }
}