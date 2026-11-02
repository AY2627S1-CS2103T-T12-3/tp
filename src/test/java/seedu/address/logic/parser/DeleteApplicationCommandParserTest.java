package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteApplicationCommand;

public class DeleteApplicationCommandParserTest {
    private final DeleteApplicationCommandParser parser = new DeleteApplicationCommandParser();

    @Test
    public void parse_validIndex_success() {
        assertParseSuccess(parser, " 2 ", new DeleteApplicationCommand(Index.fromOneBased(2)));
        assertParseSuccess(parser, " \t 1 ", new DeleteApplicationCommand(Index.fromOneBased(1)));
    }

    @Test
    public void parse_missingOrInvalidIndex_failure() {
        for (String input : new String[]{"", "0", "-1", "+1", "1.5", "one", "2147483648", "1 2", "1 n/Role"}) {
            assertParseFailure(parser, input,
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteApplicationCommand.MESSAGE_USAGE));
        }
    }
}
