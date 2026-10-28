package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Tests the tutorial count command's response for empty and populated books.
 */
public class CountCommandTest {

    @Test
    public void execute_emptyBook_reportsZeroCounts() {
        Model model = new ModelManager();
        assertEquals("0 contact(s), 0 application(s).",
                new CountCommand().execute(model).getFeedbackToUser());
    }

    @Test
    public void execute_populatedBook_reportsContactCount() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String expected = String.format(CountCommand.MESSAGE_SUCCESS,
                model.getAddressBook().getPersonList().size(),
                model.getAddressBook().getApplicationList().size());
        assertEquals(expected, new CountCommand().execute(model).getFeedbackToUser());
    }
}
