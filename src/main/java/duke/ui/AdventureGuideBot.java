package duke.ui;

import java.io.IOException;
import java.util.List;

import duke.components.Deadline;
import duke.components.Event;
import duke.components.Parser;
import duke.components.Task;
import duke.components.TaskList;
import duke.components.ToDo;
import duke.data.Storage;
import duke.exceptions.*;

public class AdventureGuideBot {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    public AdventureGuideBot() {
        ui = new Ui();
        storage = new Storage();
        try {
            List<Task> loadedTasks = storage.load();
            tasks = new TaskList(loadedTasks);
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void start() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                String[] parsedCommand = Parser.parse(fullCommand);
                String commandWord = parsedCommand[0];
                String commandArgs = parsedCommand[1];
                switch (commandWord) {
                    case "bye":
                        ui.showGoodbye();
                        isExit = true;
                        break;
                    case "list":
                        handleList();
                        break;
                    case "mark":
                        handleMark(commandArgs);
                        break;
                    case "unmark":
                        handleUnmark(commandArgs);
                        break;
                    case "todo":
                        handleTodo(commandArgs);
                        break;
                    case "deadline":
                        handleDeadline(commandArgs);
                        break;
                    case "event":
                        handleEvent(commandArgs);
                        break;
                    case "delete":
                        handleDelete(commandArgs);
                        break;
                    case "find":
                        handleFind(commandArgs);
                        break;
                    default:
                        throw new UnknownCommandException();
                }
            } catch (AdventureGuideException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("OOPS!!! An unexpected error occurred.");
                e.printStackTrace();
            } finally {
                ui.showLine();
            }
        }
    }

    private void handleList() {
        ui.showLine();
        ui.showError(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            ui.showError(" " + (i + 1) + ". " + tasks.getTask(i));
        }
    }

    private void handleMark(String args) throws InvalidTaskNumberException, IOException {
        int taskIndex = Integer.parseInt(args) - 1;
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new InvalidTaskNumberException();
        }
        tasks.getTask(taskIndex).markAsDone();
        ui.showLine();
        ui.showError(" Nice! I've marked this task as done:");
        ui.showError("   " + tasks.getTask(taskIndex));
        storage.save(tasks.getTasks());
    }

    private void handleUnmark(String args) throws InvalidTaskNumberException, IOException {
        int taskIndex = Integer.parseInt(args) - 1;
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new InvalidTaskNumberException();
        }
        tasks.getTask(taskIndex).markAsNotDone();
        ui.showLine();
        ui.showError(" OK, I've marked this task as not done yet:");
        ui.showError("   " + tasks.getTask(taskIndex));
        storage.save(tasks.getTasks());
    }

    private void handleTodo(String args) throws EmptyDescriptionException, IOException {
        if (args.isEmpty()) {
            throw new EmptyDescriptionException("todo");
        }
        tasks.addTask(new ToDo(args));
        ui.showLine();
        ui.showError(" Got it. I've added this task:");
        ui.showError("   " + tasks.getTask(tasks.size() - 1));
        ui.showError(" Now you have " + tasks.size() + " tasks in the list.");
        storage.save(tasks.getTasks());
    }

    private void handleDeadline(String args) throws EmptyDescriptionException, IOException {
        String[] parts = args.split(" /by ");
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new EmptyDescriptionException("deadline");
        }
        tasks.addTask(new Deadline(parts[0].trim(), parts[1].trim()));
        ui.showLine();
        ui.showError(" Got it. I've added this task:");
        ui.showError("   " + tasks.getTask(tasks.size() - 1));
        ui.showError(" Now you have " + tasks.size() + " tasks in the list.");
        storage.save(tasks.getTasks());
    }

    private void handleEvent(String args) throws EmptyDescriptionException, IOException {
        String[] parts = args.split(" /from | /to ");
        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new EmptyDescriptionException("event");
        }
        tasks.addTask(new Event(parts[0].trim(), parts[1].trim(), parts[2].trim()));
        ui.showLine();
        ui.showError(" Got it. I've added this task:");
        ui.showError("   " + tasks.getTask(tasks.size() - 1));
        ui.showError(" Now you have " + tasks.size() + " tasks in the list.");
        storage.save(tasks.getTasks());
    }

    private void handleDelete(String args) throws InvalidTaskNumberException, IOException {
        int taskIndex = Integer.parseInt(args) - 1;
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new InvalidTaskNumberException();
        }
        Task removedTask = tasks.removeTask(taskIndex);
        ui.showLine();
        ui.showError(" Noted. I've removed this task:");
        ui.showError("   " + removedTask);
        ui.showError(" Now you have " + tasks.size() + " tasks in the list.");
        storage.save(tasks.getTasks());
    }

    private void handleFind(String args) {
        List<Task> matchingTasks = tasks.findTasks(args);
        ui.showLine();
        ui.showError(" Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            ui.showError(" " + (i + 1) + ". " + matchingTasks.get(i));
        }
    }
}