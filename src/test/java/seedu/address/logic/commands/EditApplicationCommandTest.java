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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult.DisplayedList;
import seedu.address.logic.commands.EditApplicationCommand.EditApplicationDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.ApplicationName;
import seedu.address.model.application.Description;
import seedu.address.model.person.Company;

public class EditApplicationCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalApplicationAddressBook(), new UserPrefs());
        model.addPerson(AMY);
        model.updateFilteredPersonList(unused -> false);
    }

    @Test
    public void execute_partialEdit_preservesOtherFieldsAndContacts() throws Exception {
        EditApplicationDescriptor descriptor = new EditApplicationDescriptor();
        descriptor.setDescription(new Description("Interview scheduled."));
        Application expected = new Application(SOFTWARE_ENGINEER_INTERN.getName(),
                SOFTWARE_ENGINEER_INTERN.getCompany(),
                descriptor.getDescription().orElseThrow(), SOFTWARE_ENGINEER_INTERN.getDate());

        CommandResult result = new EditApplicationCommand(Index.fromOneBased(1), descriptor).execute(model);

        assertEquals(new CommandResult(String.format(EditApplicationCommand.MESSAGE_EDIT_APPLICATION_SUCCESS,
                Messages.format(expected)), DisplayedList.APPLICATIONS), result);
        assertEquals(List.of(expected, PRODUCT_MANAGER_INTERN), model.getAddressBook().getApplicationList());
        assertEquals(List.of(AMY), model.getAddressBook().getPersonList());
        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    @Test
    public void execute_allFieldsAtLastIndex_replacesApplicationInPlace() throws Exception {
        EditApplicationDescriptor descriptor = new EditApplicationDescriptor();
        descriptor.setName(new ApplicationName("Graduate Engineer"));
        descriptor.setCompany(new Company("New Corp"));
        descriptor.setDescription(new Description("Offer received."));
        descriptor.setDate(new ApplicationDate("2026-10-15"));
        Application expected = new Application(descriptor.getName().orElseThrow(),
                descriptor.getCompany().orElseThrow(),
                descriptor.getDescription().orElseThrow(), descriptor.getDate().orElseThrow());

        new EditApplicationCommand(Index.fromOneBased(2), descriptor).execute(model);

        assertEquals(List.of(SOFTWARE_ENGINEER_INTERN, expected), model.getAddressBook().getApplicationList());
    }

    @Test
    public void execute_sameValues_success() throws Exception {
        EditApplicationDescriptor descriptor = new EditApplicationDescriptor();
        descriptor.setName(SOFTWARE_ENGINEER_INTERN.getName());
        new EditApplicationCommand(Index.fromOneBased(1), descriptor).execute(model);
        assertEquals(getTypicalApplicationAddressBook().getApplicationList(),
                model.getAddressBook().getApplicationList());
    }

    @Test
    public void execute_caseOnlyIdentityChange_success() throws Exception {
        EditApplicationDescriptor descriptor = new EditApplicationDescriptor();
        descriptor.setName(new ApplicationName("software engineer intern"));
        descriptor.setCompany(new Company("tech corp"));
        new EditApplicationCommand(Index.fromOneBased(1), descriptor).execute(model);
        assertEquals("software engineer intern", model.getAddressBook().getApplicationList().get(0).getName().value);
    }

    @Test
    public void execute_duplicateIdentity_failureWithoutChanges() {
        EditApplicationDescriptor descriptor = new EditApplicationDescriptor();
        descriptor.setName(PRODUCT_MANAGER_INTERN.getName());
        descriptor.setCompany(PRODUCT_MANAGER_INTERN.getCompany());
        assertCommandFailure(new EditApplicationCommand(Index.fromOneBased(1), descriptor), model,
                EditApplicationCommand.MESSAGE_DUPLICATE_APPLICATION);

        descriptor.setName(new ApplicationName("product manager intern"));
        descriptor.setCompany(new Company("other corp"));
        assertCommandFailure(new EditApplicationCommand(Index.fromOneBased(1), descriptor), model,
                EditApplicationCommand.MESSAGE_DUPLICATE_APPLICATION);
    }

    @Test
    public void execute_invalidIndex_failureWithoutChanges() {
        EditApplicationDescriptor descriptor = new EditApplicationDescriptor();
        descriptor.setDescription(new Description("Interview scheduled."));
        assertCommandFailure(new EditApplicationCommand(Index.fromOneBased(3), descriptor), model,
                Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        assertCommandFailure(new EditApplicationCommand(Index.fromOneBased(1), descriptor), new ModelManager(),
                Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void constructor_mutatedDescriptor_keepsDefensiveCopy() throws Exception {
        EditApplicationDescriptor descriptor = new EditApplicationDescriptor();
        descriptor.setDescription(new Description("Interview scheduled."));
        EditApplicationCommand command = new EditApplicationCommand(Index.fromOneBased(1), descriptor);
        descriptor.setDescription(new Description("Changed after construction."));
        command.execute(model);
        assertEquals("Interview scheduled.", model.getAddressBook().getApplicationList().get(0).getDescription().value);
    }

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditApplicationCommand(null,
                new EditApplicationDescriptor()));
        assertThrows(NullPointerException.class, () -> new EditApplicationCommand(Index.fromOneBased(1), null));
    }

    @Test
    public void equals() {
        EditApplicationDescriptor descriptor = new EditApplicationDescriptor();
        descriptor.setName(SOFTWARE_ENGINEER_INTERN.getName());
        EditApplicationCommand command = new EditApplicationCommand(Index.fromOneBased(1), descriptor);
        assertTrue(command.equals(command));
        assertTrue(command.equals(new EditApplicationCommand(Index.fromOneBased(1), descriptor)));
        assertFalse(command.equals(new EditApplicationCommand(Index.fromOneBased(2), descriptor)));
        assertFalse(command.equals(new EditApplicationCommand(Index.fromOneBased(1), new EditApplicationDescriptor())));
        assertFalse(command.equals(null));
        assertFalse(command.equals(1));
    }
}
