package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.ContactContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new ContactContainsKeywordsPredicate(List.of("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_multipleWhitespace_normalizesSearchTerm() throws Exception {
        FindCommand command = parser.parse(" \n Alice \n \t Bob  \t");

        assertEquals("Alice Bob", command.getSearchTerm());
    }

    @Test
    public void parse_formattedPhoneNumber_addsNormalizedPhoneToOriginalKeywords() {
        FindCommand expectedFindCommand = new FindCommand(
                new ContactContainsKeywordsPredicate(List.of("+65", "9123-4567", "+6591234567")),
                "+65 9123-4567");

        assertParseSuccess(parser, "+65 9123-4567", expectedFindCommand);
    }

}
