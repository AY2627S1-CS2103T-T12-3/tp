package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests browsing command history and returning to an unfinished command.
 */
public class CommandHistoryTest {
    @Test
    public void browseHistory_restoresDraftAfterNewestCommand() {
        CommandHistory history = new CommandHistory();
        history.record("list");
        history.record("find Alex");

        assertEquals("find Alex", history.previous("add n/Draft"));
        assertEquals("list", history.previous("find Alex"));
        assertEquals("list", history.previous("list"));
        assertEquals("find Alex", history.next("list"));
        assertEquals("add n/Draft", history.next("find Alex"));
        assertEquals("add n/Draft", history.next("add n/Draft"));
    }

    @Test
    public void emptyHistory_keepsCurrentText() {
        CommandHistory history = new CommandHistory();
        assertEquals("draft", history.previous("draft"));
        assertEquals("draft", history.next("draft"));
    }
}
