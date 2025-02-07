package duke.data;

import duke.components.Deadline;
import duke.components.Task;
import duke.components.TaskList;
import duke.components.ToDo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Storage class.
 */
public class StorageTest {
    private Storage storage;
    private static final String TEST_FILE_PATH = "./data/test_tasks.txt";

    @BeforeEach
    public void setUp() throws IOException {
        storage = new Storage(TEST_FILE_PATH);
        Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
    }

    @Test
    public void saveAndLoadTasks_validTasks_tasksSavedAndLoaded() throws IOException {
        TaskList taskList = new TaskList();
        taskList.addTask(new ToDo("Test ToDo"));
        taskList.addTask(new Deadline("Test Deadline", "2/12/2019 1800"));

        storage.save(taskList.getTasks());

        List<Task> loadedTasks = storage.load();
        assertEquals(2, loadedTasks.size());
        assertEquals("Test ToDo", loadedTasks.get(0).getDescription());
        assertEquals("Test Deadline", loadedTasks.get(1).getDescription());
    }

    @Test
    public void loadTasks_noFile_emptyTaskList() throws IOException {
        List<Task> loadedTasks = storage.load();
        assertTrue(loadedTasks.isEmpty());
    }
}