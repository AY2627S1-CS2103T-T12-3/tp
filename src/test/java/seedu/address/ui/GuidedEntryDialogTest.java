package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.AddressBookParser;

/**
 * Tests that guided answers use the normal command syntax.
 */
public class GuidedEntryDialogTest {
    @Test
    public void buildCommand_contact_omitsOptionalBlankField() {
        String command = GuidedEntryDialog.buildCommand(false,
                List.of("Alex", "Example", "Recruiter", "alex@example.com", "91234567", ""));
        assertEquals("add n/Alex c/Example r/Recruiter e/alex@example.com p/91234567", command);
        assertDoesNotThrow(() -> new AddressBookParser().parseCommand(command));
    }

    @Test
    public void buildCommand_application_includesSelectedStatus() {
        String command = GuidedEntryDialog.buildCommand(true,
                List.of("Intern", "Example", "Applied online", "2026-10-29", "APPLIED"));
        assertEquals("addapp n/Intern c/Example d/Applied online dt/2026-10-29 s/APPLIED", command);
        assertDoesNotThrow(() -> new AddressBookParser().parseCommand(command));
    }
}
