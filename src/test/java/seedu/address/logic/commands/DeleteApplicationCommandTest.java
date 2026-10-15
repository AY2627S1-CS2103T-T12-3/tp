package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalApplications.PRODUCT_MANAGER_INTERN;
import static seedu.address.testutil.TypicalApplications.SOFTWARE_ENGINEER_INTERN;
import static seedu.address.testutil.TypicalApplications.getTypicalApplicationAddressBook;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult.DisplayedList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class DeleteApplicationCommandTest {
    private final Model model = new ModelManager(getTypicalApplicationAddressBook(), new UserPrefs());

    @Test
    public void execute_firstIndex_deletesOnlyApplicationAndPreservesContactFilter() throws Exception {
        model.addPerson(AMY);
        model.updateFilteredPersonList(unused -> false);
        CommandResult result = new DeleteApplicationCommand(Index.fromOneBased(1)).execute(model);
        assertEquals(new CommandResult(String.format(DeleteApplicationCommand.MESSAGE_DELETE_APPLICATION_SUCCESS,
                Messages.format(SOFTWARE_ENGINEER_INTERN)), DisplayedList.APPLICATIONS), result);
        assertEquals(List.of(PRODUCT_MANAGER_INTERN), model.getAddressBook().getApplicationList());
        assertEquals(List.of(AMY), model.getAddressBook().getPersonList());
        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    @Test
    public void execute_lastThenFinalIndex_leavesEmptyApplicationList() throws Exception {
        new DeleteApplicationCommand(Index.fromOneBased(2)).execute(model);
        assertEquals(List.of(SOFTWARE_ENGINEER_INTERN), model.getAddressBook().getApplicationList());
        CommandResult result = new DeleteApplicationCommand(Index.fromOneBased(1)).execute(model);
        assertTrue(model.getAddressBook().getApplicationList().isEmpty());
        assertEquals(DisplayedList.APPLICATIONS, result.getDisplayedList());
    }

    @Test
    public void execute_invalidIndex_failureWithoutChanges() {
        assertCommandFailure(new DeleteApplicationCommand(Index.fromOneBased(3)), model,
                Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        assertCommandFailure(new DeleteApplicationCommand(Index.fromOneBased(1)), new ModelManager(),
                Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteApplicationCommand(null));
    }

    @Test
    public void equals() {
        DeleteApplicationCommand command = new DeleteApplicationCommand(Index.fromOneBased(1));
        assertTrue(command.equals(command));
        assertTrue(command.equals(new DeleteApplicationCommand(Index.fromOneBased(1))));
        assertFalse(command.equals(new DeleteApplicationCommand(Index.fromOneBased(2))));
        assertFalse(command.equals(null));
        assertFalse(command.equals(1));
    }
}
