package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult.ContactSearchAction;
import seedu.address.logic.commands.CommandResult.DisplayedList;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, DisplayedList.NONE)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));

        // different displayedList value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", DisplayedList.APPLICATIONS)));

        CommandResult searchResult = new CommandResult("Found contacts", DisplayedList.CONTACTS,
                ContactSearchAction.SET, "Ada");
        assertTrue(searchResult.equals(new CommandResult("Found contacts", DisplayedList.CONTACTS,
                ContactSearchAction.SET, "Ada")));
        assertFalse(searchResult.equals(new CommandResult("Found contacts", DisplayedList.CONTACTS,
                ContactSearchAction.SET, "Grace")));
        assertFalse(searchResult.equals(new CommandResult("Found contacts", DisplayedList.CONTACTS,
                ContactSearchAction.CLEAR, "")));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());

        // different displayedList value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", DisplayedList.APPLICATIONS).hashCode());

        CommandResult searchResult = new CommandResult("Found contacts", DisplayedList.CONTACTS,
                ContactSearchAction.SET, "Ada");
        CommandResult searchResultCopy = new CommandResult("Found contacts", DisplayedList.CONTACTS,
                ContactSearchAction.SET, "Ada");
        assertEquals(searchResult.hashCode(), searchResultCopy.hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + ", displayedList=" + commandResult.getDisplayedList()
                + ", contactSearchAction=" + commandResult.getContactSearchAction()
                + ", contactSearchTerm=" + commandResult.getContactSearchTerm() + "}";
        assertEquals(expected, commandResult.toString());
    }

    @Test
    public void contactSearchActionAndTerm_areStored() {
        CommandResult commandResult = new CommandResult("Found contacts", DisplayedList.CONTACTS,
                ContactSearchAction.SET, "Ada Lovelace");

        assertEquals(ContactSearchAction.SET, commandResult.getContactSearchAction());
        assertEquals("Ada Lovelace", commandResult.getContactSearchTerm());
    }

    @Test
    public void constructor_invalidContactSearchState_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new CommandResult("Found contacts",
                DisplayedList.CONTACTS, ContactSearchAction.SET, ""));
        assertThrows(IllegalArgumentException.class, () -> new CommandResult("Listed contacts",
                DisplayedList.CONTACTS, ContactSearchAction.CLEAR, "Ada"));
        assertThrows(IllegalArgumentException.class, () -> new CommandResult("Unchanged search",
                DisplayedList.CONTACTS, ContactSearchAction.UNCHANGED, "Ada"));
    }
}
