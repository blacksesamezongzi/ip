package components;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Exceptions.*;

public class AdventureGuideBot {
    private List<Task> tasks = new ArrayList<>();

    public void start() {
        Scanner sc = new Scanner(System.in);
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm adventureGuide");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        while (true) {
            String input = sc.nextLine();
            try {
                if (input.equals("bye")) {
                    handleBye();
                    break;
                } else if (input.equals("list")) {
                    handleList();
                } else if (input.startsWith("mark ")) {
                    handleMark(input);
                } else if (input.startsWith("unmark ")) {
                    handleUnmark(input);
                } else if (input.startsWith("todo ")) {
                    handleTodo(input);
                } else if (input.startsWith("deadline ")) {
                    handleDeadline(input);
                } else if (input.startsWith("event ")) {
                    handleEvent(input);
                } else if (input.startsWith("delete ")) {
                    handleDelete(input);
                } else {
                    throw new UnknownCommandException();
                }
            } catch (AdventureGuideException e) {
                System.out.println("____________________________________________________________");
                System.out.println(" " + e.getMessage());
                System.out.println("____________________________________________________________");
            } catch (Exception e) {
                System.out.println("____________________________________________________________");
                System.out.println(" OOPS!!! An unexpected error occurred.");
                System.out.println("____________________________________________________________");
            }
        }

        sc.close();
    }

    private void handleBye() {
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }

    private void handleList() {
        System.out.println("____________________________________________________________");
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasks.get(i));
        }
        System.out.println("____________________________________________________________");
    }

    private void handleMark(String input) throws InvalidTaskNumberException {
        int taskIndex = Integer.parseInt(input.split(" ")[1]) - 1;
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            tasks.get(taskIndex).markAsDone();
            System.out.println("____________________________________________________________");
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   " + tasks.get(taskIndex));
            System.out.println("____________________________________________________________");
        } else {
            throw new InvalidTaskNumberException();
        }
    }

    private void handleUnmark(String input) throws InvalidTaskNumberException {
        int taskIndex = Integer.parseInt(input.split(" ")[1]) - 1;
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            tasks.get(taskIndex).markAsNotDone();
            System.out.println("____________________________________________________________");
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   " + tasks.get(taskIndex));
            System.out.println("____________________________________________________________");
        } else {
            throw new InvalidTaskNumberException();
        }
    }

    private void handleTodo(String input) throws EmptyDescriptionException {
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new EmptyDescriptionException("todo");
        } else {
            tasks.add(new ToDo(description));
            System.out.println("____________________________________________________________");
            System.out.println(" Got it. I've added this task:");
            System.out.println("   " + tasks.get(tasks.size() - 1));
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println("____________________________________________________________");
        }
    }

    private void handleDeadline(String input) throws EmptyDescriptionException {
        String[] parts = input.substring(9).split(" /by ");
        String description = parts[0].trim();
        String by = parts[1].trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new EmptyDescriptionException("deadline");
        }
        tasks.add(new Deadline(description, by));
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + tasks.get(tasks.size() - 1));
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    private void handleEvent(String input) throws EmptyDescriptionException {
        String[] parts = input.substring(6).split(" /from | /to ");
        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new EmptyDescriptionException("event");
        }
        tasks.add(new Event(description, from, to));
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + tasks.get(tasks.size() - 1));
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    private void handleDelete(String input) throws InvalidTaskNumberException {
        int taskIndex = Integer.parseInt(input.split(" ")[1]) - 1;
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            Task removedTask = tasks.remove(taskIndex);
            System.out.println("____________________________________________________________");
            System.out.println(" Noted. I've removed this task:");
            System.out.println("   " + removedTask);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println("____________________________________________________________");
        } else {
            throw new InvalidTaskNumberException();
        }
    }
}