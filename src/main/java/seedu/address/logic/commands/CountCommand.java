package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Shows the total number of contacts and applications without changing the current view.
 */
public class CountCommand extends Command {

    public static final String COMMAND_WORD = "count";
    public static final String MESSAGE_SUCCESS = "%1$d contact(s), %2$d application(s).";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        int contactCount = model.getAddressBook().getPersonList().size();
        int applicationCount = model.getAddressBook().getApplicationList().size();
        return new CommandResult(String.format(MESSAGE_SUCCESS, contactCount, applicationCount));
    }
}
