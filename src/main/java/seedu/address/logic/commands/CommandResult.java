package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    /** The list that the main window should display after a command completes. */
    public enum DisplayedList {
        NONE,
        CONTACTS,
        APPLICATIONS
    }

    /** Describes whether the result changes the active contact search indicator. */
    public enum ContactSearchAction {
        UNCHANGED,
        SET,
        CLEAR
    }

    private static final String MESSAGE_INVALID_CONTACT_SEARCH_STATE =
            "Contact search term must be non-empty only when the action is SET.";

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The list to display, or {@link DisplayedList#NONE} to keep the current list. */
    private final DisplayedList displayedList;
    private final ContactSearchAction contactSearchAction;
    private final String contactSearchTerm;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, DisplayedList.NONE);
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, DisplayedList displayedList) {
        this(feedbackToUser, showHelp, exit, displayedList, ContactSearchAction.UNCHANGED, "");
    }

    /**
     * Constructs a result that specifies how to handle the active contact search indicator.
     *
     * @throws IllegalArgumentException if a SET action has a blank term, or another action has a non-empty term
     * @throws NullPointerException if any argument is null
     */
    public CommandResult(String feedbackToUser, DisplayedList displayedList,
            ContactSearchAction contactSearchAction, String contactSearchTerm) {
        this(feedbackToUser, false, false, displayedList, contactSearchAction, contactSearchTerm);
    }

    private CommandResult(String feedbackToUser, boolean showHelp, boolean exit, DisplayedList displayedList,
            ContactSearchAction contactSearchAction, String contactSearchTerm) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.displayedList = requireNonNull(displayedList);
        this.contactSearchAction = requireNonNull(contactSearchAction);
        this.contactSearchTerm = requireNonNull(contactSearchTerm);
        validateContactSearchState(this.contactSearchAction, this.contactSearchTerm);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    /**
     * Constructs a {@code CommandResult} that asks the main window to show {@code displayedList}.
     */
    public CommandResult(String feedbackToUser, DisplayedList displayedList) {
        this(feedbackToUser, false, false, displayedList);
    }

    private static void validateContactSearchState(ContactSearchAction action, String searchTerm) {
        boolean isSetAction = action == ContactSearchAction.SET;
        checkArgument(isSetAction ? !searchTerm.isBlank() : searchTerm.isEmpty(),
                MESSAGE_INVALID_CONTACT_SEARCH_STATE);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public DisplayedList getDisplayedList() {
        return displayedList;
    }

    /** Returns how the main window should update the contact search indicator. */
    public ContactSearchAction getContactSearchAction() {
        return contactSearchAction;
    }

    /** Returns the search terms for the contact search indicator. */
    public String getContactSearchTerm() {
        return contactSearchTerm;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult otherCommandResult)) {
            return false;
        }

        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && displayedList == otherCommandResult.displayedList
                && contactSearchAction == otherCommandResult.contactSearchAction
                && contactSearchTerm.equals(otherCommandResult.contactSearchTerm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, displayedList, contactSearchAction, contactSearchTerm);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("displayedList", displayedList)
                .add("contactSearchAction", contactSearchAction)
                .add("contactSearchTerm", contactSearchTerm)
                .toString();
    }

}
