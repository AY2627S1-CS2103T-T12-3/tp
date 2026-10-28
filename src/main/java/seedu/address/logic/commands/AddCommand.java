package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WHERE_MET;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult.DisplayedList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a contact to InternTrack.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a contact to InternTrack. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_COMPANY + "COMPANY "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_WHERE_MET + "WHERE_MET\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_COMPANY + "Google "
            + PREFIX_ROLE + "SWE Recruiter "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_WHERE_MET + "Career fair 2026";

    public static final String MESSAGE_SUCCESS = "New contact added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This contact already exists in InternTrack.";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)), DisplayedList.CONTACTS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand otherAddCommand)) {
            return false;
        }

        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
