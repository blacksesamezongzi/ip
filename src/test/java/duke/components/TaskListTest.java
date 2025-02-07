package duke.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the TaskList class.
 */
public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    @Test
    public void addTask_validTask_taskAdded() {
        Task task = new ToDo("Test task");
        taskList.addTask(task);
        assertEquals(1, taskList.size());
        assertEquals(task, taskList.getTask(0));
    }

    @Test
    public void removeTask_validIndex_taskRemoved() {
        Task task = new ToDo("Test task");
        taskList.addTask(task);
        Task removedTask = taskList.removeTask(0);
        assertEquals(0, taskList.size());
        assertEquals(task, removedTask);
    }

    @Test
    public void getTask_invalidIndex_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.getTask(0));
    }
}