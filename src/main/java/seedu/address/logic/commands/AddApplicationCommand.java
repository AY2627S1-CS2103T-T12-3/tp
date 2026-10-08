package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;

/**
 * Adds an application to InternTrack.
 */
public class AddApplicationCommand extends Command {

    public static final String COMMAND_WORD = "addapp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an application to InternTrack. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_COMPANY + "COMPANY "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_DATE + "YYYY-MM-DD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Software Engineer Intern "
            + PREFIX_COMPANY + "Tech Corp "
            + PREFIX_DESCRIPTION + "Applied through the company careers page. "
            + PREFIX_DATE + "2026-10-07";

    public static final String MESSAGE_SUCCESS = "New application added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPLICATION = "This application already exists in InternTrack.";

    private final Application toAdd;

    /**
     * Creates an AddApplicationCommand to add the specified {@code Application}.
     */
    public AddApplicationCommand(Application application) {
        requireNonNull(application);
        toAdd = application;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasApplication(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPLICATION);
        }

        model.addApplication(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddApplicationCommand otherAddApplicationCommand)) {
            return false;
        }

        return toAdd.equals(otherAddApplicationCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
