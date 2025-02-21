package duke.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import duke.components.Deadline;
import duke.components.Event;
import duke.components.TaskList;
import duke.data.Storage;
import duke.exceptions.EmptyDescriptionException;
import duke.exceptions.EmptyIndexException;
import duke.exceptions.InvalidDateFormatException;
import duke.exceptions.InvalidTaskNumberException;

/**
 * Tests methods handleDeadline, handleTag and handleUntag in AdventureGuideBot class.
 */
public class AdventureGuideBotTest {
    private boolean isLoaded = true;
    private TaskList tasks;
    private Storage storage;
    private Ui ui;
    private AdventureGuideBot adventureGuideBot;
    private DateTimeFormatter EventInputFormatter = Event.getInputFormatter();
    private DateTimeFormatter DeadlineInputFormatter = Deadline.getInputFormatter();

    @BeforeEach
    public void setUp() {
        tasks = new TaskList();
        storage = new Storage("data/tasks.txt");
        adventureGuideBot = new AdventureGuideBot();
    }

    @Test
    public void handleDeadline_emptyDescription_exceptionThrown() {
        try {
            adventureGuideBot.handleDeadline("");
            fail();
        } catch (EmptyDescriptionException e) {
            assertEquals("OOPS!!! The description of a deadline cannot be empty.", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void handleDeadline_wrongDateFormat_exceptionThrown() {
        try {
            adventureGuideBot.handleDeadline("return book /by 2025-2-21");
            fail();
        } catch (InvalidDateFormatException e) {
            assertEquals("OOPS!!! The date format is invalid. Please use the format 'dd/MM/yyyy HHmm'.", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test 
    public void handleTag_emptyDescription_exceptionThrown() {
        try {
            adventureGuideBot.handleTag("");
            fail();
        } catch (EmptyDescriptionException e) {
            assertEquals("OOPS!!! The description of a tag cannot be empty.", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test 
    public void handleTag_indexNotInt_exceptionThrown() {
        try {
            adventureGuideBot.handleTag("great 1");
            fail();
        } catch (InvalidTaskNumberException e) {
            assertEquals("OOPS!!! The task number is invalid.", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void handleTag_indexOutOfBound_exceptionThrown() {
        try {
            adventureGuideBot.handleTag("10 great");
            fail();
        } catch (InvalidTaskNumberException e) {
            assertEquals("OOPS!!! The task number is invalid.", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void handleUntag_emptyIndex_exceptionThrown() {
        try {
            adventureGuideBot.handleUntag("");
            fail();
        } catch (EmptyIndexException e) {
            assertEquals("OOPS!!! The index of a untag cannot be empty.", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }
}
    
