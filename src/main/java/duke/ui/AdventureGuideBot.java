package duke.ui;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import duke.components.Deadline;
import duke.components.Event;
import duke.components.Parser;
import duke.components.Task;
import duke.components.TaskList;
import duke.components.ToDo;
import duke.data.Storage;
import duke.exceptions.*;

/**
 * The bot that interacts with the user and manages tasks.
 */
public class AdventureGuideBot {
    private boolean isLoaded = true;
    private TaskList tasks;
    private Storage storage;
    private Ui ui;
    private DateTimeFormatter EventInputFormatter = Event.getInputFormatter();
    private DateTimeFormatter DeadlineInputFormatter = Deadline.getInputFormatter();

    /**
     * Constructs an AdventureGuideBot and initializes the storage and task list.
     */
    public AdventureGuideBot() {

        storage = new Storage();
        ui = new Ui();
        try {
            List<Task> loadedTasks = storage.load();
            tasks = new TaskList(loadedTasks);
        } catch (IOException e) {
            tasks = new TaskList();
            isLoaded = false;
        }
    }

    /**
     * Returns the response to the user input.
     * 
     * @param input The user input.
     * @return The response to the user input.
     */
    public String getResponse(String input) {
        try {
            String[] parsedCommand = Parser.parse(input);
            String commandWord = parsedCommand[0];
            String commandArgs = parsedCommand[1];
            return executeCommand(commandWord, commandArgs);
        } catch (AdventureGuideException e) {
            return e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "OOPS!!! An unexpected error occurred.";
        }
    }

    /**
     * Executes the command based on the command word and arguments.
     * 
     * @param commandWord The command word.
     * @param commandArgs The command arguments.
     * @return The response to the command.
     * @throws AdventureGuideException If an error occurs during command execution.
     * @throws IOException If an error occurs during file operations.
     */
    private String executeCommand(String commandWord, String commandArgs) throws AdventureGuideException, IOException {
        switch (commandWord) {
        case "bye":
            return "Farewell, adventurer! May your path be clear and your tasks conquered. Until our next quest!";
        case "list":
            return handleList();
        case "mark":
            return handleMark(commandArgs);
        case "unmark":
            return handleUnmark(commandArgs);
        case "todo":
            return handleTodo(commandArgs);
        case "deadline":
            return handleDeadline(commandArgs);
        case "event":
            return handleEvent(commandArgs);
        case "delete":
            return handleDelete(commandArgs);
        case "find":
            return handleFind(commandArgs);
        case "tag":
            return handleTag(commandArgs);
        case "untag":
            return handleUntag(commandArgs);
        default:
            throw new UnknownCommandException();
        }
    }

    public String getLoadingError() {
        return isLoaded ? null : "I'm sorry, but I couldn't load your tasks. Starting with an empty list instead.";
    }

    /**
     * Handles the "list" command.
     * 
     * @return The response to the "list" command, listing all tasks.
     */
    public String handleList() {
        return "Here are the tasks in your list:\n" +
                IntStream.range(0, tasks.size())
                        .mapToObj(i -> (i + 1) + ". " + tasks.getTask(i))
                        .collect(Collectors.joining("\n"));

    }

    /**
     * Handles the "mark" command.
     * 
     * @param args The task number to mark as done.
     * @return The response to the "mark" command, marking the task as done.
     * @throws InvalidTaskNumberException If the task number is invalid.
     */
    public String handleMark(String args) throws InvalidTaskNumberException, IOException {
        int taskIndex = Integer.parseInt(args) - 1;
        assert taskIndex >= 0 && taskIndex < tasks.size() : "Invalid task index";
        validateTaskIndex(taskIndex);
        tasks.getTask(taskIndex).markAsDone();
        storage.save(tasks.getTasks());
        return "Nice! I've marked this task as done:\n" + tasks.getTask(taskIndex);
    }

    /**
     * Handles the "unmark" command.
     * 
     * @param args The task number to mark as not done.
     * @return The response to the "unmark" command, marking the task as not done.
     * @throws InvalidTaskNumberException If the task number is invalid.
     */
    public String handleUnmark(String args) throws InvalidTaskNumberException, IOException {
        int taskIndex = Integer.parseInt(args) - 1;
        assert taskIndex >= 0 && taskIndex < tasks.size() : "Invalid task index";
        validateTaskIndex(taskIndex);
        tasks.getTask(taskIndex).markAsNotDone();
        storage.save(tasks.getTasks());
        return "OK, I've marked this task as not done yet:\n" + tasks.getTask(taskIndex);
    }

    /**
     * Handles the "todo" command.
     * 
     * @param args The description of the todo task.
     * @return The response to the "todo" command, adding the todo task.
     * @throws EmptyDescriptionException If the task description is empty.
     */
    public String handleTodo(String args) throws EmptyDescriptionException, IOException {
        assert args != null : "Task description cannot be null";
        validateDescription(args, "todo");
        Task task = new ToDo(args);
        tasks.addTask(task);
        storage.save(tasks.getTasks());
        return "Got it. I've added this task:\n" + task + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Handles the "deadline" command.
     * 
     * @param args The description and deadline of the deadline task.
     * @return The response to the "deadline" command, adding the deadline task.
     * @throws EmptyDescriptionException If the task description is empty.
     * @throws InvalidDateFormatException If the deadline format is invalid.
     * @throws IOException If an error occurs during file operations.
     */
    public String handleDeadline(String args) throws EmptyDescriptionException, InvalidDateFormatException, IOException {
        String[] parts = args.split(" /by ");
        validateDeadlineOrEvent(parts, "deadline");
        tasks.addTask(new Deadline(parts[0].trim(), parts[1].trim()));
        Task task = new Deadline(parts[0].trim(), parts[1].trim());
        tasks.addTask(task);
        storage.save(tasks.getTasks());
        return "Got it. I've added this task:\n" + task + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Handles the event command.
     * 
     * @param args The description and event time of the event task.
     * @return The response to event command, adding the event task.
     * @throws EmptyDescriptionException If the task description is empty.
     * @throws InvalidDateFormatException If the event format is invalid.
     * @throws IOException If an error occurs during file operations.
     */
    public String handleEvent(String args) throws EmptyDescriptionException, InvalidDateFormatException, IOException {
        String[] parts = args.split(" /from | /to ");
        assert parts.length == 3 : "Invalid event format";
        validateDeadlineOrEvent(parts, "event");
        Task task = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
        tasks.addTask(task);
        storage.save(tasks.getTasks());
        return "Got it. I've added this task:\n" + task + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Handles the "delete" command.
     * 
     * @param args The task number to delete.
     * @return The response to the "delete" command, deleting the task.
     * @throws InvalidTaskNumberException If the task number is invalid.
     * @throws IOException If an error occurs during file operations.
     */
    public String handleDelete(String args) throws InvalidTaskNumberException, IOException {
        int taskIndex = Integer.parseInt(args) - 1;
        assert taskIndex >= 0 && taskIndex < tasks.size() : "Invalid task index";
        validateTaskIndex(taskIndex);
        Task removedTask = tasks.removeTask(taskIndex);
        storage.save(tasks.getTasks());
        return "Noted. I've removed this task:\n" + removedTask + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Handles the "find" command.
     * 
     * @param args The keyword to search for.
     * @return The response to the "find" command, listing the matching tasks.
     */
    public String handleFind(String args) {
        List<Task> matchingTasks = tasks.findTasks(args);
        StringBuilder response = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            response.append((i + 1)).append(". ").append(matchingTasks.get(i)).append("\n");
        }
        return response.toString();
    }

    /**
     * Handles the "tag" command.
     * 
     * @param args The task number and tag to add.
     * @return The response to the "tag" command, adding the tag to the task if tag is not already present, else updating the tag.
     * @throws InvalidTaskNumberException If the task number is invalid.
     * @throws EmptyDescriptionException If the tag is empty.
     * @throws IOException If an error occurs during file operations.
     */
    public String handleTag(String args) throws InvalidTaskNumberException, EmptyDescriptionException, IOException {
        String[] parts = args.split(" ");
        validateNonEmptyTag(parts);
        validateTextInt(parts[0]);
        int taskIndex = Integer.parseInt(parts[0]) - 1;
        validateTaskIndex(taskIndex);
        boolean hasTag = tasks.getTask(taskIndex).hasTag();
        String oldTag = hasTag ? tasks.getTask(taskIndex).getTag() : "no tag";
        String newTag = parts[1];
        tasks.getTask(taskIndex).setTag(newTag);
        storage.save(tasks.getTasks());
        return hasTag ? "The tag #" + oldTag + " has been updated to #" + newTag : "A tag #" + newTag + " has been added to the task.";
    }

    /**
     * Handles the "untag" command.
     * 
     * @param args The task number to remove the tag from.
     * @return The response to the "untag" command, removing the tag from the task.
     * @throws InvalidTaskNumberException If the task number is invalid.
     * @throws IOException If an error occurs during file operations.
     */
    public String handleUntag(String args) throws EmptyIndexException, InvalidTaskNumberException, IOException {
        validateNonEmptyUntag(args);
        validateTextInt(args);
        int taskIndex = Integer.parseInt(args) - 1;
        assert taskIndex >= 0 && taskIndex < tasks.size() : "Invalid task index";
        validateTaskIndex(taskIndex);
        boolean hasTag = tasks.getTask(taskIndex).hasTag();
        if (!hasTag) {
            return "This task does not have a tag.";
        } else {
            String oldTag = tasks.getTask(taskIndex).getTag();
            tasks.getTask(taskIndex).setTag(null);
            storage.save(tasks.getTasks());
            return "The tag #" + oldTag + " has been removed from the task.";
        }
    }

    private void validateTaskIndex(int taskIndex) throws InvalidTaskNumberException {
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new InvalidTaskNumberException();
        }
    }

    private void validateDescription(String description, String taskType) throws EmptyDescriptionException {
        if (description.isEmpty()) {
            throw new EmptyDescriptionException(taskType);
        }
    }

    private void validateDeadlineOrEvent(String[] parts, String taskType) throws EmptyDescriptionException, InvalidDateFormatException {
        if (taskType.equals("deadline") && (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty())) {
            throw new EmptyDescriptionException(taskType);
        } else if (taskType.equals("event") && (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty())) {
            throw new EmptyDescriptionException(taskType);
        }
        try {
            if (taskType.equals("deadline")) {
                LocalDateTime.parse(parts[1].trim(), DeadlineInputFormatter);
            } else {
                LocalDateTime.parse(parts[1].trim(), EventInputFormatter);
                LocalDateTime.parse(parts[2].trim(), EventInputFormatter);
            }
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException();
        }
    }

    private void validateNonEmptyTag(String[] parts) throws EmptyDescriptionException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new EmptyDescriptionException("tag");
        }
    }

    private void validateTextInt(String text) throws InvalidTaskNumberException {
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException();
        }
    }

    private void validateNonEmptyUntag(String arg) throws EmptyIndexException {
        if (arg.equals(null) || arg.isEmpty()) {
            throw new EmptyIndexException("untag");
        }
    }

    public Ui getUi() {
        return this.ui;
    }
}