package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.CommandResult.DisplayedList;
import seedu.address.model.Model;

/**
 * Lists all contacts in InternTrack to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all %1$d contact(s).";

    public static final String MESSAGE_EMPTY = "You have no contacts yet. Use the add command to add one.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        int contactCount = model.getFilteredPersonList().size();
        if (contactCount == 0) {
            return new CommandResult(MESSAGE_EMPTY, DisplayedList.CONTACTS);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, contactCount), DisplayedList.CONTACTS);
    }
}
