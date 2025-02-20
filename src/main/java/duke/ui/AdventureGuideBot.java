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
            ui.showLoadingError();
        }
    }

    public String getResponse(String input) {
        try {
            String[] parsedCommand = Parser.parse(input);
            String commandWord = parsedCommand[0];
            String commandArgs = parsedCommand[1];
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
            default:
                throw new UnknownCommandException();
            }
            } catch (AdventureGuideException e) {
                return e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                return "OOPS!!! An unexpected error occurred.";
            }
        }
        
    private String handleList() {
        return "Here are the tasks in your list:\n" +
                IntStream.range(0, tasks.size())
                        .mapToObj(i -> (i + 1) + ". " + tasks.getTask(i))
                        .collect(Collectors.joining("\n"));
    }

    private String handleMark(String args) throws InvalidTaskNumberException, IOException {
        int taskIndex = Integer.parseInt(args) - 1;
        assert taskIndex >= 0 && taskIndex < tasks.size() : "Invalid task index";
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new InvalidTaskNumberException();
        }
        tasks.getTask(taskIndex).markAsDone();
        storage.save(tasks.getTasks());
        String responses = "Nice! I've marked this task as done:\n" + tasks.getTask(taskIndex);
        return responses;
    }

    private String handleUnmark(String args) throws InvalidTaskNumberException, IOException {
        int taskIndex = Integer.parseInt(args) - 1;
        assert taskIndex >= 0 && taskIndex < tasks.size() : "Invalid task index";
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new InvalidTaskNumberException();
        }
        tasks.getTask(taskIndex).markAsNotDone();
        storage.save(tasks.getTasks());
        return "OK, I've marked this task as not done yet:\\n" + tasks.getTask(taskIndex);
    }

    private String handleTodo(String args) throws EmptyDescriptionException, IOException {
        assert args != null : "Task description cannot be null";
        if (args.isEmpty()) {
            throw new EmptyDescriptionException("todo");
        }
        tasks.addTask(new ToDo(args));
        String text = " Got it. I've added this task:\n" + "   " + tasks.getTask(tasks.size() - 1) + "\n" + " Now you have "
                + tasks.size() + " tasks in the list.";
        storage.save(tasks.getTasks());
        return text;
    }

    private String handleDeadline(String args) throws EmptyDescriptionException, InvalidDateFormatException, IOException {
        String[] parts = args.split(" /by ");
        assert parts.length == 2 : "Invalid deadline format";
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new EmptyDescriptionException("deadline");
        } 
        try {
            LocalDateTime.parse(parts[1].trim(), DeadlineInputFormatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException();
        }
        tasks.addTask(new Deadline(parts[0].trim(), parts[1].trim()));
        String text = " Got it. I've added this task:\n" + "   " + tasks.getTask(tasks.size() - 1) + "\n" + " Now you have "
                + tasks.size() + " tasks in the list.";
        storage.save(tasks.getTasks());
        return text;
    }

    private String handleEvent(String args) throws EmptyDescriptionException, InvalidDateFormatException, IOException {
        String[] parts = args.split(" /from | /to ");
        assert parts.length == 3 : "Invalid event format";
        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new EmptyDescriptionException("event");
        }
        try {
            LocalDateTime.parse(parts[1].trim(), EventInputFormatter);
            LocalDateTime.parse(parts[2].trim(), EventInputFormatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException();
        }
        tasks.addTask(new Event(parts[0].trim(), parts[1].trim(), parts[2].trim()));
        String text = " Got it. I've added this task:\n" + "   " + tasks.getTask(tasks.size() - 1) + "\n" + " Now you have "
                + tasks.size() + " tasks in the list.";
        storage.save(tasks.getTasks());
        return text;
    }

    private String handleDelete(String args) throws InvalidTaskNumberException, IOException {
        int taskIndex = Integer.parseInt(args) - 1;
        assert taskIndex >= 0 && taskIndex < tasks.size() : "Invalid task index";
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new InvalidTaskNumberException();
        }
        Task removedTask = tasks.removeTask(taskIndex);
        String text = " Noted. I've removed this task:\n" + "   " + removedTask + "\n" + " Now you have " + tasks.size()
                + " tasks in the list.";
        storage.save(tasks.getTasks());
        return text;
    }

    private String handleFind(String args) {
        List<Task> matchingTasks = tasks.findTasks(args);
        String text = " Here are the matching tasks in your list:\n";
        for (int i = 0; i < matchingTasks.size(); i++) {
            text += " " + (i + 1) + ". " + matchingTasks.get(i) + "\n";
        }
        return text;
    }

    public Ui getUi() {
        return this.ui;
    }
}