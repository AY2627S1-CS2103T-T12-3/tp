package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommandBoxTest {

    @Test
    public void getQuickSearchText_emptyInput_preparesFindCommand() {
        assertEquals("find ", CommandBox.getQuickSearchText(""));
    }

    @Test
    public void getQuickSearchText_whitespaceInput_preparesFindCommand() {
        assertEquals("find ", CommandBox.getQuickSearchText("   \t"));
    }

    @Test
    public void getQuickSearchText_existingCommand_preservesText() {
        String command = "add n/Alex Yeoh";

        assertEquals(command, CommandBox.getQuickSearchText(command));
    }
}
