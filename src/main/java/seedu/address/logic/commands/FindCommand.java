package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult.ContactSearchAction;
import seedu.address.logic.commands.CommandResult.DisplayedList;
import seedu.address.model.Model;
import seedu.address.model.person.ContactContainsKeywordsPredicate;

/**
 * Finds and lists all contacts with any argument keyword in any contact field.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all contacts whose fields contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private static final String MESSAGE_SEARCH_TERM_CANNOT_BE_BLANK = "Search term must not be blank.";

    private final ContactContainsKeywordsPredicate predicate;
    private final String searchTerm;

    /**
     * Creates a find command using the predicate's keywords as the displayed search term.
     *
     * @throws IllegalArgumentException if the resulting search term is blank
     */
    public FindCommand(ContactContainsKeywordsPredicate predicate) {
        this(requireNonNull(predicate), String.join(" ", predicate.getKeywords()));
    }

    /**
     * Creates a {@code FindCommand} with a predicate and displayable search terms.
     *
     * @throws IllegalArgumentException if the search term is blank
     */
    public FindCommand(ContactContainsKeywordsPredicate predicate, String searchTerm) {
        this.predicate = requireNonNull(predicate);
        this.searchTerm = requireNonNull(searchTerm);
        checkArgument(!this.searchTerm.isBlank(), MESSAGE_SEARCH_TERM_CANNOT_BE_BLANK);
    }

    /** Returns the normalized search terms shown in the contact list. */
    public String getSearchTerm() {
        return searchTerm;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                DisplayedList.CONTACTS, ContactSearchAction.SET, searchTerm);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand otherFindCommand)) {
            return false;
        }

        return predicate.equals(otherFindCommand.predicate)
                && searchTerm.equals(otherFindCommand.searchTerm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(predicate, searchTerm);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .add("searchTerm", searchTerm)
                .toString();
    }
}
