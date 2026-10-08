package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.ApplicationName;
import seedu.address.model.application.Description;
import seedu.address.model.person.Company;

public class AddApplicationCommandTest {

    private static final Application SOFTWARE_ENGINEER_INTERN = new Application(
            new ApplicationName("Software Engineer Intern"), new Company("Tech Corp"),
            new Description("Applied through the company careers page."), new ApplicationDate("2026-10-07"));
    private static final Application PRODUCT_MANAGER_INTERN = new Application(
            new ApplicationName("Product Manager Intern"), new Company("Other Corp"),
            new Description("Applied through a university careers portal."), new ApplicationDate("2026-10-08"));

    @Test
    public void constructor_nullApplication_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddApplicationCommand(null));
    }

    @Test
    public void execute_applicationAcceptedByModel_addSuccessful() throws Exception {
        Model model = new ModelManager();

        CommandResult commandResult = new AddApplicationCommand(SOFTWARE_ENGINEER_INTERN).execute(model);

        assertEquals(String.format(AddApplicationCommand.MESSAGE_SUCCESS, Messages.format(SOFTWARE_ENGINEER_INTERN)),
                commandResult.getFeedbackToUser());
        assertTrue(model.hasApplication(SOFTWARE_ENGINEER_INTERN));
    }

    @Test
    public void execute_duplicateApplication_throwsCommandException() {
        Model model = new ModelManager();
        model.addApplication(SOFTWARE_ENGINEER_INTERN);
        AddApplicationCommand command = new AddApplicationCommand(SOFTWARE_ENGINEER_INTERN);

        assertThrows(CommandException.class, AddApplicationCommand.MESSAGE_DUPLICATE_APPLICATION, () ->
                command.execute(model));
    }

    @Test
    public void equals() {
        AddApplicationCommand addSoftwareEngineerCommand = new AddApplicationCommand(SOFTWARE_ENGINEER_INTERN);
        AddApplicationCommand addProductManagerCommand = new AddApplicationCommand(PRODUCT_MANAGER_INTERN);

        // same object -> returns true
        assertTrue(addSoftwareEngineerCommand.equals(addSoftwareEngineerCommand));

        // same values -> returns true
        AddApplicationCommand addSoftwareEngineerCommandCopy = new AddApplicationCommand(SOFTWARE_ENGINEER_INTERN);
        assertTrue(addSoftwareEngineerCommand.equals(addSoftwareEngineerCommandCopy));

        // different types -> returns false
        assertFalse(addSoftwareEngineerCommand.equals(1));

        // null -> returns false
        assertFalse(addSoftwareEngineerCommand.equals(null));

        // different application -> returns false
        assertFalse(addSoftwareEngineerCommand.equals(addProductManagerCommand));
    }

    @Test
    public void toStringMethod() {
        AddApplicationCommand command = new AddApplicationCommand(SOFTWARE_ENGINEER_INTERN);
        String expected = AddApplicationCommand.class.getCanonicalName()
                + "{toAdd=" + SOFTWARE_ENGINEER_INTERN + "}";

        assertEquals(expected, command.toString());
    }
}
