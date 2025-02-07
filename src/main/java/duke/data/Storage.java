package duke.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import duke.components.*;

public class Storage {
    private static final String FILE_PATH = "./data/tasks.txt";

    public List<Task> load() throws IOException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);
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

    public void save(List<Task> tasks) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
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