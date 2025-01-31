package components;

import java.util.Scanner;
import Exceptions.*;

public class AdventureGuideBot {
    private Task[] tasks = new Task[100];
    private int taskCount = 0;

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
        for (int i = 0; i < taskCount; i++) {
            System.out.println(" " + (i + 1) + ". " + tasks[i]);
        }
        System.out.println("____________________________________________________________");
    }

    private void handleMark(String input) throws InvalidTaskNumberException {
        int taskIndex = Integer.parseInt(input.split(" ")[1]) - 1;
        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsDone();
            System.out.println("____________________________________________________________");
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   " + tasks[taskIndex]);
            System.out.println("____________________________________________________________");
        } else {
            throw new InvalidTaskNumberException();
        }
    }

    private void handleUnmark(String input) throws InvalidTaskNumberException {
        int taskIndex = Integer.parseInt(input.split(" ")[1]) - 1;
        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsNotDone();
            System.out.println("____________________________________________________________");
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   " + tasks[taskIndex]);
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
            tasks[taskCount] = new ToDo(description);
            taskCount++;
            System.out.println("____________________________________________________________");
            System.out.println(" Got it. I've added this task:");
            System.out.println("   " + tasks[taskCount - 1]);
            System.out.println(" Now you have " + taskCount + " tasks in the list.");
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
        tasks[taskCount] = new Deadline(description, by);
        taskCount++;
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + tasks[taskCount - 1]);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
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
        tasks[taskCount] = new Event(description, from, to);
        taskCount++;
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + tasks[taskCount - 1]);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }
}