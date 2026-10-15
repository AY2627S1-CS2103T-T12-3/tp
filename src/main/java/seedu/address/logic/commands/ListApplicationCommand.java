package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult.DisplayedList;
import seedu.address.model.Model;

/**
 * Lists all applications in InternTrack to the user.
 */
public class ListApplicationCommand extends Command {

    public static final String COMMAND_WORD = "listapp";

    public static final String MESSAGE_SUCCESS = "Listed all %1$d application(s).";

    public static final String MESSAGE_EMPTY = "You have no applications yet. Use the addapp command to add one.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        int applicationCount = model.getAddressBook().getApplicationList().size();
        if (applicationCount == 0) {
            return new CommandResult(MESSAGE_EMPTY, DisplayedList.APPLICATIONS);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, applicationCount), DisplayedList.APPLICATIONS);
    }
}
