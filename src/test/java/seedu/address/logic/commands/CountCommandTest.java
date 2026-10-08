package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Contains integration tests for {@code CountCommand}.
 */
public class CountCommandTest {

    @Test
    public void execute_emptyAddressBook_returnsZero() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new CountCommand(), model, "Number of displayed contacts: 0", expectedModel);
    }

    @Test
    public void execute_allPersons_returnsTotalCount() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        assertCommandSuccess(new CountCommand(), model, "Number of displayed contacts: 7", expectedModel);
    }

    @Test
    public void execute_filteredPersons_returnsDisplayedCount() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(List.of("Kurz"));
        model.updateFilteredPersonList(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(new CountCommand(), model, "Number of displayed contacts: 1", expectedModel);
    }
}
