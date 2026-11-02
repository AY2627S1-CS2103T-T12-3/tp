package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult.DisplayedList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.ApplicationName;
import seedu.address.model.application.Description;
import seedu.address.model.person.Company;

/**
 * Contains integration and unit tests for {@code ListApplicationCommand}.
 */
public class ListApplicationCommandTest {

    private static final Application SOFTWARE_ENGINEER_INTERN = new Application(
            new ApplicationName("Software Engineer Intern"), new Company("Tech Corp"),
            new Description("Applied through the company careers page."), new ApplicationDate("2026-10-07"));

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        model.addApplication(SOFTWARE_ENGINEER_INTERN);
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_applicationListNotEmpty_showsApplicationCount() {
        String expectedMessage = String.format(ListApplicationCommand.MESSAGE_SUCCESS, 1);
        assertCommandSuccess(new ListApplicationCommand(), model,
                new CommandResult(expectedMessage, DisplayedList.APPLICATIONS), expectedModel);
    }

    @Test
    public void execute_applicationListEmpty_showsEmptyMessage() {
        Model emptyModel = new ModelManager();
        assertCommandSuccess(new ListApplicationCommand(), emptyModel,
                new CommandResult(ListApplicationCommand.MESSAGE_EMPTY, DisplayedList.APPLICATIONS),
                new ModelManager());
    }
}
