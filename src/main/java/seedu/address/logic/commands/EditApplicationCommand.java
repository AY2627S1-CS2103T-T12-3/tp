package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult.DisplayedList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.ApplicationName;
import seedu.address.model.application.ApplicationStatus;
import seedu.address.model.application.Description;
import seedu.address.model.person.Company;

/**
 * Edits an application identified by its index in the application list.
 */
public class EditApplicationCommand extends Command {

    private static final Logger logger = LogsCenter.getLogger(EditApplicationCommand.class);

    public static final String COMMAND_WORD = "editapp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the application identified by the index number used in the application list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_COMPANY + "COMPANY] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_DATE + "YYYY-MM-DD] "
            + "[" + PREFIX_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_DESCRIPTION + "Interview scheduled. "
            + PREFIX_DATE + "2026-10-15 " + PREFIX_STATUS + "INTERVIEWING";

    public static final String MESSAGE_EDIT_APPLICATION_SUCCESS = "Edited application: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_APPLICATION = "This application already exists in InternTrack.";

    private final Index index;
    private final EditApplicationDescriptor editApplicationDescriptor;

    /**
     * Creates a command to edit the application at {@code index} with the supplied details.
     */
    public EditApplicationCommand(Index index, EditApplicationDescriptor editApplicationDescriptor) {
        this.index = requireNonNull(index);
        this.editApplicationDescriptor = new EditApplicationDescriptor(requireNonNull(editApplicationDescriptor));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> applications = model.getAddressBook().getApplicationList();
        if (index.getZeroBased() >= applications.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        Application applicationToEdit = applications.get(index.getZeroBased());
        Application editedApplication = createEditedApplication(applicationToEdit, editApplicationDescriptor);
        if (!applicationToEdit.isSameApplication(editedApplication) && model.hasApplication(editedApplication)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPLICATION);
        }

        model.setApplication(applicationToEdit, editedApplication);
        if (applicationToEdit.getStatus() != editedApplication.getStatus()) {
            logger.info(() -> String.format("Updated application status at index %d: %s -> %s",
                    index.getOneBased(), applicationToEdit.getStatus(), editedApplication.getStatus()));
        }
        return new CommandResult(String.format(MESSAGE_EDIT_APPLICATION_SUCCESS, Messages.format(editedApplication)),
                DisplayedList.APPLICATIONS);
    }

    private static Application createEditedApplication(Application original, EditApplicationDescriptor descriptor) {
        ApplicationName name = descriptor.getName().orElse(original.getName());
        Company company = descriptor.getCompany().orElse(original.getCompany());
        Description description = descriptor.getDescription().orElse(original.getDescription());
        ApplicationDate date = descriptor.getDate().orElse(original.getDate());
        ApplicationStatus status = descriptor.getStatus().orElse(original.getStatus());
        return new Application(name, company, description, date, status);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EditApplicationCommand otherCommand)) {
            return false;
        }
        return index.equals(otherCommand.index)
                && editApplicationDescriptor.equals(otherCommand.editApplicationDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editApplicationDescriptor", editApplicationDescriptor)
                .toString();
    }

    /**
     * Stores supplied application fields; omitted fields retain their existing values.
     */
    public static class EditApplicationDescriptor {
        private ApplicationName name;
        private Company company;
        private Description description;
        private ApplicationDate date;
        private ApplicationStatus status;

        public EditApplicationDescriptor() {}

        /**
         * Copies the supplied edit details.
         */
        public EditApplicationDescriptor(EditApplicationDescriptor toCopy) {
            name = toCopy.name;
            company = toCopy.company;
            description = toCopy.description;
            date = toCopy.date;
            status = toCopy.status;
        }

        /**
         * Returns true if at least one field is supplied.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, company, description, date, status);
        }

        public void setName(ApplicationName name) {
            this.name = name;
        }

        public Optional<ApplicationName> getName() {
            return Optional.ofNullable(name);
        }

        public void setCompany(Company company) {
            this.company = company;
        }

        public Optional<Company> getCompany() {
            return Optional.ofNullable(company);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setDate(ApplicationDate date) {
            this.date = date;
        }

        public Optional<ApplicationDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setStatus(ApplicationStatus status) {
            this.status = status;
        }

        public Optional<ApplicationStatus> getStatus() {
            return Optional.ofNullable(status);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof EditApplicationDescriptor otherDescriptor)) {
                return false;
            }

            return Objects.equals(name, otherDescriptor.name)
                    && Objects.equals(company, otherDescriptor.company)
                    && Objects.equals(description, otherDescriptor.description)
                    && Objects.equals(date, otherDescriptor.date)
                    && status == otherDescriptor.status;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("company", company)
                    .add("description", description)
                    .add("date", date)
                    .add("status", status)
                    .toString();
        }
    }
}
