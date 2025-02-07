package duke.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import duke.components.*;

/**
 * Handles loading tasks from the file and saving tasks to the file.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage object with the default file path.
     */
    public Storage() {
        this.filePath = "./data/tasks.txt";
    }

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The file path to use for storage.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     *
     * @return A list of tasks loaded from the file.
     * @throws IOException If an I/O error occurs.
     */
    public List<Task> load() throws IOException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return tasks;
        }

        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String input = sc.nextLine();
            // System.out.println("Reading line: " + input); // Debug statement
            String[] taskComponents = input.split(" \\| ");
            String type = taskComponents[0];
            boolean isDone = taskComponents[1].equals("1");
            String description = taskComponents[2];
            Task task = null;
            try {
                switch (type) {
                case "T":
                    task = new ToDo(description, isDone);
                    break;
                case "D":
                    task = new Deadline(description, taskComponents[3], isDone);
                    break;
                case "E":
                    task = new Event(description, taskComponents[3], taskComponents[4], isDone);
                    break;
                default:
                    throw new IOException("Invalid task type in file");
                }
            } catch (Exception e) {
                System.out.println("Error parsing line: " + input); // Debug statement
                e.printStackTrace(); // Print stack trace for debugging
                throw e;
            }
            tasks.add(task);
        }
        sc.close();
        return tasks;
    }

    /**
     * Saves tasks to the file.
     *
     * @param tasks The list of tasks to save.
     * @throws IOException If an I/O error occurs.
     */
    public void save(List<Task> tasks) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (Task task : tasks) {
            String type = task instanceof ToDo ? "T" : task instanceof Deadline ? "D" : "E";
            String isDone = task.isDone() ? "1" : "0";
            String description = task.getDescription();
            String line = type + " | " + isDone + " | " + description;
            if (task instanceof Deadline) {
                line += " | " + ((Deadline) task).getBy();
            } else if (task instanceof Event) {
                line += " | " + ((Event) task).getFrom() + " | " + ((Event) task).getTo();
            }
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }
}